package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.service.TaskService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// =============================================
// TASK CONTROLLER — STEP 03: GET & POST + VALIDATION
// =============================================
// NEW: REST controller for task operations.
// Handles GET (list all, get by ID) and POST
// (create with validation).
//
// PATTERN: Same structure as ProjectController —
// @RestController, @RequestMapping, constructor
// injection, GET + POST endpoints. Applying the
// same patterns to a second domain entity.
//
// NOTE: This controller handles tasks at /api/tasks.
// Tasks belong to a project (via projectId), but
// the URL structure is flat — not nested under
// /api/projects/{id}/tasks. Both approaches are
// valid in REST API design.
// =============================================
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    // Service dependency injected via constructor
    private final TaskService taskService;

    // PATTERN: Constructor injection — same as ProjectController
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks — returns all tasks
    @GetMapping
    public List<Task> getTasks() {
        return taskService.findAll();
    }

    // GET /api/tasks/{id} — returns a single task by ID
    // PATTERN: Same @PathVariable pattern as ProjectController
    @GetMapping("/{id}")
    public Task getTaskById(@PathVariable Long id) {
        return taskService.findById(id);
    }

    // POST /api/tasks — create a new task with validation
    // =============================================
    // PATTERN: Same POST pattern as ProjectController:
    // @Valid + @RequestBody + ResponseEntity(201).
    //
    // NOTE: Task model includes @NotNull on projectId
    // — the task MUST reference a project.
    // The service also checks that the project exists.
    // This is two layers of validation:
    //   1. @NotNull: ensures projectId is present in
    //      the request body (HTTP layer)
    //   2. projectService.findById(): ensures the
    //      project actually exists (business layer)
    // =============================================
    @PostMapping
    public ResponseEntity<Task> createTask(@Valid @RequestBody Task task) {
        Task created = taskService.create(
                task.getTitle(),
                task.getDescription(),
                task.getProjectId()
        );
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }
}
