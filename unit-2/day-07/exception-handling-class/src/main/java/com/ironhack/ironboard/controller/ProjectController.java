package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.model.Project;
import com.ironhack.ironboard.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// =============================================
// PROJECT CONTROLLER — FULL CRUD + EXCEPTIONS
// =============================================
// CHANGED: Exception handling is now
// centralized via @ControllerAdvice.
//
// KEY PATTERN: Notice how NONE of the controller methods have
// try-catch blocks. The controller's job is to delegate to the
// service and return the result. If the service throws an exception
// (e.g., ResourceNotFoundException), it propagates OUT of the
// controller method, and the GlobalExceptionHandler catches it
// and returns the proper HTTP error response.
//
// PATTERN: Exception propagation flow:
//   getProjectById(999)
//     → projectService.findById(999)        // service throws ResourceNotFoundException
//       → exception propagates up           // no try-catch in controller
//         → GlobalExceptionHandler catches  // returns 404 JSON
//
// COMMON MISTAKE: Writing try-catch in every controller method:
//   try {
//       return projectService.findById(id);
//   } catch (ResourceNotFoundException e) {
//       return ResponseEntity.notFound().build();
//   }
//   This WORKS but is redundant — the GlobalExceptionHandler already does this.
//   The whole point of @ControllerAdvice is to avoid this repetition.
// =============================================
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    // Constructor injection — Spring provides the ProjectService bean automatically
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // =============================================
    // GET /api/projects
    // GET /api/projects?name=Iron
    // =============================================
    @GetMapping
    public List<Project> getProjects(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return projectService.findByName(name);
        }
        return projectService.findAll();
    }

    // GET /api/projects/{id}
    // WHY: No try-catch here! If findById() throws ResourceNotFoundException,
    // it propagates out of this method, and GlobalExceptionHandler catches it.
    // The client receives a 404 JSON response automatically.
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    // POST /api/projects
    // NOTE: If @Valid fails (e.g., name is blank), Spring throws
    // MethodArgumentNotValidException BEFORE this method body runs.
    // GlobalExceptionHandler.handleValidationErrors() catches it → 400 JSON.
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project created = projectService.create(project.getName(), project.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // =============================================
    // PUT ENDPOINT — FULL UPDATE
    // =============================================
    // TIP: Two possible exceptions can occur here:
    //   1. @Valid fails → MethodArgumentNotValidException → 400 (validation error)
    //   2. Project not found → ResourceNotFoundException → 404 (not found)
    // Both are handled by GlobalExceptionHandler — no try-catch needed.
    // =============================================

    /**
     * PUT /api/projects/{id}
     * Full update — requires all fields (validated with @Valid on the model).
     * Returns 200 OK with the updated project.
     * Throws ResourceNotFoundException (→ 404) if not found.
     */
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @Valid @RequestBody Project project) {
        return projectService.fullUpdate(id, project.getName(), project.getDescription());
    }

    // =============================================
    // PATCH ENDPOINT — PARTIAL UPDATE
    // =============================================
    // No @Valid — partial updates should not enforce required-field constraints.
    // =============================================

    /**
     * PATCH /api/projects/{id}
     * Partial update — only non-null fields are applied.
     * No @Valid because PATCH must accept partial payloads.
     * Returns 200 OK with the updated project.
     * Throws ResourceNotFoundException (→ 404) if not found.
     */
    @PatchMapping("/{id}")
    public Project patchProject(@PathVariable Long id,
                                @RequestBody Project project) {
        return projectService.partialUpdate(id, project.getName(), project.getDescription());
    }

    // =============================================
    // DELETE ENDPOINT
    // =============================================
    // Returns 204 No Content. If the project doesn't exist,
    // ResourceNotFoundException propagates → GlobalExceptionHandler → 404.
    // =============================================

    /**
     * DELETE /api/projects/{id}
     * Deletes a project. Returns 204 No Content (empty body).
     * Throws ResourceNotFoundException (→ 404) if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
