package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.model.Task;
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
// TASK CONTROLLER — STEP 04: FULL CRUD + EXCEPTIONS
// =============================================
// WHY: Same pattern as ProjectController — no try-catch blocks.
// Exceptions thrown by TaskService propagate out of controller methods
// and are caught by GlobalExceptionHandler.
//
// PATTERN: The exception propagation applies to ALL endpoints that
// call findById() (directly or indirectly through fullUpdate/partialUpdate/delete):
//   - GET /{id}    → findById()         → ResourceNotFoundException possible
//   - PUT /{id}    → fullUpdate()       → calls findById() → ResourceNotFoundException possible
//   - PATCH /{id}  → partialUpdate()    → calls findById() → ResourceNotFoundException possible
//   - DELETE /{id} → delete()           → calls findById() → ResourceNotFoundException possible
// =============================================
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Constructor injection — Spring provides the TaskService bean automatically
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
     * Throws ResourceNotFoundException (→ 404) if not found.
     */
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    /**
     * POST /api/tasks
     * Creates a new task. The request body is validated using @Valid.
     * Returns 201 Created with the new task.
     */
    // NOTE: @Valid triggers validation on the Task model (@NotBlank, @NotNull, @Size).
    // If validation fails → MethodArgumentNotValidException → GlobalExceptionHandler → 400 JSON.
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
    // PUT ENDPOINT — FULL UPDATE
    // =============================================
    // fullUpdate() takes title, description, and type. Status is NOT
    // changeable via PUT — use PATCH for that.
    // =============================================

    /**
     * PUT /api/tasks/{id}
     * Full update — requires all fields (validated with @Valid on the model).
     * Updates title, description, and type. Status uses PATCH.
     * Returns 200 OK with the updated task.
     * Throws ResourceNotFoundException (→ 404) if not found.
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
    // PATCH ENDPOINT — PARTIAL UPDATE
    // =============================================
    // PATCH can change ANY field including status and type.
    // No @Valid — partial updates should not enforce required-field constraints.
    //
    // Example PATCH requests:
    //   {"status": "IN_PROGRESS"}  — only changes status
    //   {"title": "New Title"}     — only changes title
    //   {"status": "DONE", "type": "BUG"} — changes both
    // =============================================

    /**
     * PATCH /api/tasks/{id}
     * Partial update — only non-null fields are applied.
     * Allows changing title, description, status, and/or type independently.
     * Returns 200 OK with the updated task.
     * Throws ResourceNotFoundException (→ 404) if not found.
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
    // DELETE ENDPOINT
    // =============================================

    /**
     * DELETE /api/tasks/{id}
     * Deletes a task. Returns 204 No Content (empty body).
     * Throws ResourceNotFoundException (→ 404) if not found.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
