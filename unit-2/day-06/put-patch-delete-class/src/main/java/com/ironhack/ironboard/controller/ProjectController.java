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
// PROJECT CONTROLLER — STEP 04: PUT, PATCH, DELETE
// =============================================
// CHANGED from Step 03: Added three new endpoints:
//
// 1. PUT  /api/projects/{id}  — full update
// 2. PATCH /api/projects/{id} — partial update
// 3. DELETE /api/projects/{id} — delete
//
// Also refactored: the name filter logic that was
// inline in Step 03 now calls ProjectService.findByName().
//
// KEY CONCEPTS — PUT vs PATCH:
//
// PUT = "Replace the entire resource"
//   - Client must send ALL fields
//   - Missing fields are treated as intentionally blank
//   - Uses @Valid on the Project model — validation
//     annotations on the model fields ensure name is
//     present and within size limits.
//
// PATCH = "Update only the fields I'm sending"
//   - Client sends ONLY the fields they want to change
//   - Missing/null fields are left untouched
//   - No @Valid — the model has validation annotations
//     on required fields, but PATCH must accept partial
//     payloads where those fields may be null.
//
// DELETE = "Remove the resource"
//   - No request body needed
//   - Returns 204 No Content (empty body)
//   - WHY 204 and not 200: HTTP spec says 204 means
//     "the request succeeded but there's no content to
//     return". Since the resource is gone, there's
//     nothing to return.
//
// =============================================
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    // Constructor injection -- Spring provides the ProjectService bean automatically
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // =============================================
    // GET /api/projects
    // GET /api/projects?name=Iron
    //
    // CHANGED: The name filter logic that was inline
    // now calls projectService.findByName().
    // This keeps business logic in the service layer.
    // =============================================
    @GetMapping
    public List<Project> getProjects(@RequestParam(required = false) String name) {
        if (name != null && !name.isBlank()) {
            return projectService.findByName(name);
        }
        return projectService.findAll();
    }

    // GET /api/projects/{id}
    @GetMapping("/{id}")
    public Project getProjectById(@PathVariable Long id) {
        return projectService.findById(id);
    }

    // POST /api/projects
    // PATTERN: Same POST pattern as Step 03.
    @PostMapping
    public ResponseEntity<Project> createProject(@Valid @RequestBody Project project) {
        Project created = projectService.create(project.getName(), project.getDescription());
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // =============================================
    // NEW: PUT ENDPOINT — FULL UPDATE
    // =============================================
    // @PutMapping("/{id}") — maps HTTP PUT requests
    // to this method. The {id} is extracted via
    // @PathVariable.
    //
    // PATTERN: PUT uses the same Project model as POST
    // with @Valid. A full update requires the same
    // mandatory fields as creation (name is required).
    // The validation annotations on the model ensure
    // all required fields are present.
    //
    // NOTE: Returns 200 OK (default). We don't wrap
    // in ResponseEntity because the default 200 status
    // is correct for an update.
    // =============================================

    /**
     * PUT /api/projects/{id}
     * Full update -- requires all fields (validated with @Valid on the model).
     * Returns 200 OK with the updated project.
     * Throws RuntimeException (-> 500) if not found.
     */
    @PutMapping("/{id}")
    public Project updateProject(@PathVariable Long id,
                                 @Valid @RequestBody Project project) {
        return projectService.fullUpdate(id, project.getName(), project.getDescription());
    }

    // =============================================
    // NEW: PATCH ENDPOINT — PARTIAL UPDATE
    // =============================================
    // @PatchMapping("/{id}") — maps HTTP PATCH requests
    // to this method.
    //
    // PATTERN: PATCH accepts the same Project model but
    // WITHOUT @Valid. The client can send any subset of
    // fields. Null fields are ignored by the service.
    //
    // WHY no @Valid: The Project model has @NotBlank on
    // name. If we added @Valid here, PATCH would reject
    // requests that omit name — breaking the whole
    // point of partial updates.
    //
    // KEY DIFFERENCE from PUT: PUT calls fullUpdate()
    // which replaces ALL fields unconditionally. PATCH
    // calls partialUpdate() which only updates non-null
    // fields, leaving the rest unchanged.
    //
    // NOTE: This means PATCH payloads are NOT validated.
    // A request like {"name": ""} would be accepted.
    // This is a known trade-off — proper PATCH validation
    // will be addressed when we introduce DTOs.
    //
    // =============================================

    /**
     * PATCH /api/projects/{id}
     * Partial update -- only non-null fields are applied.
     * No @Valid because PATCH must accept partial payloads
     * (the model has @NotBlank on name, which would reject requests omitting name).
     * Returns 200 OK with the updated project.
     * Throws RuntimeException (-> 500) if not found.
     */
    @PatchMapping("/{id}")
    public Project patchProject(@PathVariable Long id,
                                @RequestBody Project project) {
        return projectService.partialUpdate(id, project.getName(), project.getDescription());
    }

    // =============================================
    // NEW: DELETE ENDPOINT
    // =============================================
    // @DeleteMapping("/{id}") — maps HTTP DELETE
    // requests to this method.
    //
    // PATTERN: ResponseEntity.noContent().build()
    //   - noContent() sets the status to 204
    //   - build() creates the response with no body
    //   - The return type is ResponseEntity<Void>
    //     because there's nothing to return
    //
    // WHY 204 and not 200: After deletion, the resource
    // no longer exists. Returning 200 with the deleted
    // object is valid but uncommon. 204 is the REST
    // convention for "success, nothing to return".
    //
    // WHY ResponseEntity<Void>: The generic type is
    // Void (capital V) because the response body is
    // empty. Using ResponseEntity<Project> would
    // compile but is misleading — there's no Project
    // in the response.
    // =============================================

    /**
     * DELETE /api/projects/{id}
     * Deletes a project. Returns 204 No Content (empty body).
     * Throws RuntimeException (-> 500) if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
