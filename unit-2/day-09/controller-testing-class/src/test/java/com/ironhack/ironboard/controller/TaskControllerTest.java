// Unit tests for TaskController using @WebMvcTest and MockMvc.
// Same approach as ProjectControllerTest, focused on TaskController.
package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.exception.ResourceNotFoundException;
import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.model.TaskStatus;
import com.ironhack.ironboard.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TaskController.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TaskService taskService;

    private Task createTestTask(Long id, String title, String description,
                                TaskStatus status, String type, Long projectId) {
        Task task = new Task();
        task.setId(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(status);
        task.setType(type);
        task.setProjectId(projectId);
        return task;
    }

    @Test
    void getTasks_returnsOk() throws Exception {
        // Arrange
        Task t1 = createTestTask(1L, "Set up project", "Create Spring Boot project",
                TaskStatus.DONE, "FEATURE", 1L);
        Task t2 = createTestTask(2L, "Fix search bug", "Search returns wrong results",
                TaskStatus.IN_PROGRESS, "BUG", 2L);
        when(taskService.findAll()).thenReturn(List.of(t1, t2));

        // Act & Assert
        // TaskStatus enum values (DONE, IN_PROGRESS) are serialized as strings
        // in the JSON response — the mapper converts them via .name().
        mockMvc.perform(get("/api/tasks"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Set up project"))
                .andExpect(jsonPath("$[0].status").value("DONE"))
                .andExpect(jsonPath("$[0].type").value("FEATURE"))
                .andExpect(jsonPath("$[1].title").value("Fix search bug"))
                .andExpect(jsonPath("$[1].status").value("IN_PROGRESS"));
    }

    @Test
    void createTask_validRequest_returnsCreated() throws Exception {
        // Arrange
        Task created = createTestTask(4L, "Write tests", "Add MockMVC tests",
                TaskStatus.TODO, "FEATURE", 1L);
        when(taskService.create(any())).thenReturn(created);

        String requestBody = """
                {
                    "title": "Write tests",
                    "description": "Add MockMVC tests",
                    "type": "FEATURE",
                    "projectId": 1
                }
                """;

        // Act & Assert
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(4))
                .andExpect(jsonPath("$.title").value("Write tests"))
                .andExpect(jsonPath("$.status").value("TODO"))
                .andExpect(jsonPath("$.projectId").value(1));
    }

    @Test
    void createTask_invalidBody_returns400() throws Exception {
        // Two required fields missing — two validation errors.
        String requestBody = """
                {
                    "description": "No title or projectId"
                }
                """;

        // Act & Assert
        // Each filter expression checks for one specific error in the array.
        mockMvc.perform(post("/api/tasks")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.fieldErrors[?(@ == 'title: Task title is required')]").exists())
                .andExpect(jsonPath("$.fieldErrors[?(@ == 'projectId: Project ID is required')]").exists());
    }

    @Test
    void updateTask_validRequest_returnsUpdatedTask() throws Exception {
        // Arrange
        Task updated = createTestTask(1L, "Updated title", "Updated description",
                TaskStatus.IN_PROGRESS, "BUG", 1L);
        when(taskService.fullUpdate(eq(1L), any(Task.class))).thenReturn(updated);

        String requestBody = """
                {
                    "title": "Updated title",
                    "description": "Updated description",
                    "type": "BUG"
                }
                """;

        // Act & Assert
        mockMvc.perform(put("/api/tasks/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.title").value("Updated title"))
                .andExpect(jsonPath("$.type").value("BUG"));
    }

    @Test
    void deleteTask_existingId_returnsNoContent() throws Exception {
        // Arrange
        doNothing().when(taskService).delete(1L);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/1"))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteTask_notFound_returns404() throws Exception {
        // Arrange
        doThrow(new ResourceNotFoundException("Task", 999L))
                .when(taskService).delete(999L);

        // Act & Assert
        mockMvc.perform(delete("/api/tasks/999"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Task not found with id: 999"));
    }
}
