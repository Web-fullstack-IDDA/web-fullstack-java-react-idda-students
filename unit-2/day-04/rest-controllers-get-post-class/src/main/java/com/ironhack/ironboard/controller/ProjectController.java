package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.model.Project;
import com.ironhack.ironboard.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

// =============================================
// PROJECT CONTROLLER — STEP 03: GET & POST + VALIDATION
// =============================================
// CHANGED from Step 02: Added three new capabilities:
// 1. GET /api/projects/{id}   — retrieve one by ID
//    using @PathVariable
// 2. GET /api/projects?name=X — optional name filter
//    using @RequestParam
// 3. POST /api/projects       — create with @Valid
//    request body
//
// KEY ANNOTATIONS INTRODUCED:
//
// @PathVariable: Extracts a value from the URL path.
//   GET /api/projects/1 --> id = 1
//   The variable name in {id} must
//   match the parameter name, or you must use
//   @PathVariable("id"). Mismatch causes a 500 error.
//
// @RequestParam: Extracts a query parameter from the URL.
//   GET /api/projects?name=Iron --> name = "Iron"
//   TIP: required = false makes the parameter optional.
//   Without required = false, Spring returns 400 if
//   the parameter is missing.
//
// @RequestBody: Tells Spring to parse the HTTP body
//   as JSON and map it to the specified Java object.
//
// @Valid: Triggers Jakarta Validation on the request
//   body BEFORE the method runs. If validation fails,
//   Spring returns 400 Bad Request automatically.
//
// ResponseEntity: Wraps the response body + status code.
//   WHY: @GetMapping methods return 200 OK by default.
//   For POST, we want 201 Created — ResponseEntity lets
//   us set any HTTP status.
// =============================================
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    // Service dependency injected via constructor
    private final ProjectService projectService;

    // Constructor injection -- Spring provides the ProjectService bean automatically
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // =============================================
    // GET /api/projects
    // GET /api/projects?name=Iron
    //
    // CHANGED: Added optional name filter using @RequestParam.
    // If no "name" parameter is provided, returns all projects.
    // If "name" is provided, returns only projects whose name
    // contains the value (case-insensitive).
    //
    // PATTERN: @RequestParam(required = false) makes the
    // parameter optional. Without required = false, Spring
    // returns 400 Bad Request if the parameter is missing.
    // =============================================
    @GetMapping
    public List<Project> getProjects(@RequestParam(required = false) String name) {
        List<Project> projects = projectService.findAll();

        // NOTE: This filtering could also be done with Java Streams (.stream().filter().collect()),
        // but we have not covered Streams yet.
        if (name != null && !name.isBlank()) {
            List<Project> filtered = new ArrayList<>();
            String lowerName = name.toLowerCase();
            for (Project p : projects) {
                if (p.getName().toLowerCase().contains(lowerName)) {
                    filtered.add(p);
                }
            }
            projects = filtered;
        }

        return projects;
    }

    // NEW: Get a single project by ID
    // =============================================
    // PATTERN: @PathVariable extracts the {id} segment
    // from the URL path.
    //   GET /api/projects/1 → id = 1
    // =============================================
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    // NEW: Create a new project
    // =============================================
    // PATTERN: POST endpoint with @Valid + @RequestBody.
    //
    // The flow is:
    // 1. Client sends JSON body --> Spring deserializes
    //    into Project (@RequestBody)
    // 2. @Valid triggers Jakarta Validation -- if name
    //    is blank or too short, Spring returns 400
    //    BEFORE this method runs
    // 3. Controller extracts validated fields and calls
    //    the service
    // 4. Service creates the entity with server-generated
    //    fields (id, createdAt)
    // 5. ResponseEntity wraps the result with 201 Created
    //
    // WHY 201 and not 200: HTTP spec says 201 means
    // "a new resource was created". Using the correct
    // status code makes your API RESTful and easier
    // for clients to handle.
    //
    // COMMON MISTAKE: Returning 200 for POST. It works
    // but violates REST conventions. Clients may not
    // know a resource was created.
    // =============================================
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project created = projectService.create(project.getName(), project.getDescription());
        // PATTERN: ResponseEntity.status(201).body(created) — sets status + body
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
