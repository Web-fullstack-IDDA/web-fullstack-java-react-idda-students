package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.model.TaskStatus;
import com.ironhack.ironboard.service.TaskService;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// =============================================
// TASK CONTROLLER — STEP 04: PUT, PATCH, DELETE
// =============================================
// CHANGED from Step 03: Added PUT, PATCH, and DELETE
// endpoints for full CRUD. Same patterns as
// ProjectController.
//
// INTERESTING DIFFERENCE: Task PUT vs PATCH
//
// PUT receives the Task model with @Valid, which
// validates title (@NotBlank) and projectId (@NotNull).
// The PUT endpoint passes null for status and type,
// meaning those fields are preserved (not overwritten).
//
// PATCH receives the Task model WITHOUT @Valid.
// All fields (title, description, status, type) are
// optional. PATCH is the ONLY way to change a task's
// status or type.
//
// WHY: When creating a task (POST), the status is
// auto-set to TODO and type is optional. PUT "recreates"
// the task with the same fields as POST. But PATCH
// allows fine-grained changes to any field — including
// status transitions (TODO -> IN_PROGRESS -> DONE).

// =============================================
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Constructor injection -- Spring provides the TaskService bean automatically
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    /**
     * GET /api/tasks
     * Returns all tasks.
     */
    @GetMapping
    public List<Task> getTasks() {
        return taskService.findAll();
    }

    /**
     * GET /api/tasks/{id}
     * Returns a single task by ID.
     * Throws RuntimeException (-> 500) if not found.
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    /**
     * POST /api/tasks
     * Creates a new task. The request body is validated using @Valid.
     * @Valid triggers validation of annotations on the Task model fields
     * (title must not be blank, projectId must not be null).
     * Returns 201 Created with the new task.
     */
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.create(
                task.getTitle(),
                task.getDescription(),
                task.getProjectId(),
                task.getType()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    // =============================================
    // NEW: PUT ENDPOINT — FULL UPDATE
    // =============================================
    // PATTERN: Uses the same Task model as POST with
    // @Valid for validation. The model's @NotBlank on
    // title and @NotNull on projectId ensure all
    // required fields are present.
    //
    // NOTE: fullUpdate() takes title, description, and type.
    // Status is NOT changeable via PUT — use PATCH for that.
    //
    // WHY: When creating a task (POST), status defaults
    // to TODO. PUT "recreates" the task with the same
    // fields. Status transitions should go through PATCH.
    // =============================================

    /**
     * PUT /api/tasks/{id}
     * Full update -- requires all fields (validated with @Valid on the model).
     * Updates title, description, and type. Status uses PATCH.
     * Returns 200 OK with the updated task.
     * Throws RuntimeException (-> 500) if not found.
     */
    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id,
                           @Valid @RequestBody Task task) {
        return taskService.fullUpdate(
                id,
                task.getTitle(),
                task.getDescription(),
                task.getType()
        );
    }

    // =============================================
    // NEW: PATCH ENDPOINT — PARTIAL UPDATE
    // =============================================
    // PATTERN: Uses the Task model WITHOUT @Valid.
    // All fields (title, description, status, type)
    // are optional. Null fields are ignored by the
    // service's partialUpdate().
    //
    // WHY no @Valid: The Task model has @NotBlank on
    // title and @NotNull on projectId. If we added
    // @Valid here, PATCH would reject requests that
    // omit title or projectId — breaking the whole
    // point of partial updates.
    //
    // KEY DIFFERENCE from PUT: PUT calls fullUpdate()
    // which only handles title + description. PATCH
    // calls partialUpdate() which can update ANY field
    // including status and type.
    //
    // Example PATCH requests:
    //   {"status": "IN_PROGRESS"}  — only changes status
    //   {"title": "New Title"}     — only changes title
    //   {"status": "DONE", "type": "BUG"} — changes both
    //
    // NOTE: PATCH payloads are NOT validated. A request
    // like {"title": ""} would be accepted. This is a
    // known trade-off — proper PATCH validation will
    // be addressed when we introduce DTOs.
    //
    // =============================================

    /**
     * PATCH /api/tasks/{id}
     * Partial update -- only non-null fields are applied.
     * No @Valid because PATCH must accept partial payloads
     * (the model has @NotBlank on title and @NotNull on projectId,
     * which would reject requests omitting those fields).
     * Allows changing title, description, status, and/or type independently.
     * Returns 200 OK with the updated task.
     * Throws RuntimeException (-> 500) if not found.
     */
    @PatchMapping("/{id}")
    public Task patchTask(@PathVariable Long id,
                          @RequestBody Task task) {
        return taskService.partialUpdate(
                id,
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getType()
        );
    }

    // =============================================
    // NEW: DELETE ENDPOINT
    // =============================================
    // PATTERN: Same as ProjectController.deleteProject().
    // Returns ResponseEntity<Void> with 204 No Content.
    // =============================================

    /**
     * DELETE /api/tasks/{id}
     * Deletes a task. Returns 204 No Content (empty body).
     * Throws RuntimeException (-> 500) if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
