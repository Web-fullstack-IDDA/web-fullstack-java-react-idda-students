    // Unit tests for ProjectController using @WebMvcTest and MockMvc.
    package com.ironhack.ironboard.controller;

    import com.ironhack.ironboard.exception.ResourceNotFoundException;
    import com.ironhack.ironboard.model.Project;
    import com.ironhack.ironboard.service.ProjectService;
    import org.junit.jupiter.api.Test;
    import org.springframework.beans.factory.annotation.Autowired;
    import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
    import org.springframework.test.context.bean.override.mockito.MockitoBean;
    import org.springframework.http.MediaType;
    import org.springframework.test.web.servlet.MockMvc;

    import java.time.LocalDateTime;
    import java.util.List;

    import static org.mockito.ArgumentMatchers.any;
    import static org.mockito.ArgumentMatchers.eq;
    import static org.mockito.Mockito.doNothing;
    import static org.mockito.Mockito.doThrow;
    import static org.mockito.Mockito.when;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
    import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
    import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
    import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

    // @WebMvcTest loads only the web layer for ProjectController.
    // It does NOT start the full application — no services, no database.
    // This makes focused on the controller behavior.
    @WebMvcTest(ProjectController.class)
    class ProjectControllerTest {

        // MockMvc simulates HTTP requests (GET, POST, PUT, DELETE) without
        // starting a real server. It tests URL mapping, JSON handling,
        // validation, and exception handling — all in one fluent chain.
        // Field injection using @Autowired, standard approach in testing
        // because JUnit creates the instance not Spring.
        @Autowired
        private MockMvc mockMvc;

        // @MockitoBean creates a fake ProjectService and places it in the
        // Spring test context. The controller receives this mock instead of
        // the real service. We control what it returns with when().thenReturn().
        @MockitoBean
        private ProjectService projectService;

        // Utility method to avoid repeating setup code in every test.
        // We use a fixed date so assertions are predictable.
        private Project createTestProject(Long id, String name, String description) {
            Project project = new Project();
            project.setId(id);
            project.setName(name);
            project.setDescription(description);
            project.setCreatedAt(LocalDateTime.of(2026, 2, 7, 10, 0, 0));
            return project;
        }

        // "throws Exception" --> If something goes wrong, JUnit catches the exception
        // and fails the test for us.
        @Test
        void getProjects_returnsOk() throws Exception {
            // Arrange: configure mock behavior for this test.
            // when().thenReturn() tells the mock what to return
            // when the controller calls projectService.findAll().
            Project p1 = createTestProject(1L, "IronBoard", "A project management app");
            Project p2 = createTestProject(2L, "IronLibrary", "A library management system");
            when(projectService.findAll()).thenReturn(List.of(p1, p2));

            // Act & Assert: simulate GET /api/projects and check the response.
            mockMvc.perform(get("/api/projects"))
                    .andDo(print())                                               // prints request/response details to console — useful when debugging tests
                    .andExpect(status().isOk())                                   // HTTP 200
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))  // response is JSON
                    .andExpect(jsonPath("$.length()").value(2))                   // root array has 2 items
                    .andExpect(jsonPath("$[0].name").value("IronBoard"))          // first item's name
                    .andExpect(jsonPath("$[1].name").value("IronLibrary"));       // second item's name
        }

        @Test
        void getProjectById_existingId_returnsProject() throws Exception {
            // Arrange
            Project project = createTestProject(1L, "IronBoard", "A project management app");
            when(projectService.findById(1L)).thenReturn(project);

            // Act & Assert
            // For single-object responses, jsonPath uses "$.field" (root object)
            // instead of "$[0].field" (array element).
            mockMvc.perform(get("/api/projects/1"))
                    .andExpect(status().isOk())
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("IronBoard"))
                    .andExpect(jsonPath("$.description").value("A project management app"))
                    .andExpect(jsonPath("$.createdAt").value("2026-02-07T10:00:00")); // Jackson serializes LocalDateTime as ISO-8601
        }

        @Test
        void getProjectById_notFound_returns404() throws Exception {
            // Arrange: thenThrow() makes the mock throw an exception.
            // The controller doesn't catch it — the GlobalExceptionHandler
            // converts it to a 404 JSON error response.
            when(projectService.findById(999L))
                    .thenThrow(new ResourceNotFoundException("Project", 999L));

            // Act & Assert
            mockMvc.perform(get("/api/projects/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.message").value("Project not found with id: 999"));
        }

        @Test
        void createProject_validRequest_returnsCreated() throws Exception {
            // Arrange
            // any(Project.class) matches any Project argument.
            // We use it because the controller calls ProjectMapper.toModel(request),
            // which creates a new Project object we can't predict.
            Project created = createTestProject(3L, "New Project", "A brand new project");
            when(projectService.create(any(Project.class))).thenReturn(created);

            String requestBody = """
                    {
                        "name": "New Project",
                        "description": "A brand new project"
                    }
                    """;

            // Act & Assert
            // POST requests need contentType (tells Spring the body is JSON)
            // and content (the JSON body itself).
            mockMvc.perform(post("/api/projects")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isCreated())   // HTTP 201
                    .andExpect(jsonPath("$.id").value(3))
                    .andExpect(jsonPath("$.name").value("New Project"));
        }

        @Test
        void createProject_invalidBody_returns400() throws Exception {
            // No mock setup needed — validation fails BEFORE the service is called.
            String requestBody = """
                    {
                        "description": "No name provided"
                    }
                    """;

            // Act & Assert
            mockMvc.perform(post("/api/projects")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isBadRequest())
                    .andExpect(jsonPath("$.status").value(400))
                    // JSONPath filter expression: checks if fieldErrors array contains this exact string.
                    // The [?(...)] syntax filters array elements where the condition is true.
                    // @ refers to the current element being evaluated.
                    .andExpect(jsonPath("$.fieldErrors[?(@ == 'name: Project name is required')]").exists());
        }

        @Test
        void updateProject_validRequest_returnsUpdatedProject() throws Exception {
            // Arrange
            // eq(1L) matches the exact ID. When using any() for one argument,
            // all arguments must use matchers — you can't mix raw values with matchers.
            Project updated = createTestProject(1L, "Updated Name", "Updated description");
            when(projectService.fullUpdate(eq(1L), any(Project.class))).thenReturn(updated);

            String requestBody = """
                    {
                        "name": "Updated Name",
                        "description": "Updated description"
                    }
                    """;

            // Act & Assert
            mockMvc.perform(put("/api/projects/1")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(requestBody))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.id").value(1))
                    .andExpect(jsonPath("$.name").value("Updated Name"))
                    .andExpect(jsonPath("$.description").value("Updated description"));
        }

        @Test
        void deleteProject_existingId_returnsNoContent() throws Exception {
            // Arrange
            // For void methods, stubbing syntax is reversed:
            // doNothing().when(service).method() instead of when(service.method()).thenReturn()
            doNothing().when(projectService).delete(1L);

            // Act & Assert
            mockMvc.perform(delete("/api/projects/1"))
                    .andExpect(status().isNoContent());   // HTTP 204
        }

        @Test
        void deleteProject_notFound_returns404() throws Exception {
            // Arrange
            // doThrow() is the void-method equivalent of thenThrow()
            doThrow(new ResourceNotFoundException("Project", 999L))
                    .when(projectService).delete(999L);

            // Act & Assert
            mockMvc.perform(delete("/api/projects/999"))
                    .andExpect(status().isNotFound())
                    .andExpect(jsonPath("$.status").value(404))
                    .andExpect(jsonPath("$.message").value("Project not found with id: 999"));
        }
    }
