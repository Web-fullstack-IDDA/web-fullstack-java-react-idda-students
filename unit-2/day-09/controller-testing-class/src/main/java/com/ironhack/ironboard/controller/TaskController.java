package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.dto.request.CreateTaskRequest;
import com.ironhack.ironboard.dto.request.UpdateTaskRequest;
import com.ironhack.ironboard.dto.response.TaskResponse;
import com.ironhack.ironboard.mapper.TaskMapper;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for task CRUD operations.
 * Accepts request DTOs and returns TaskResponse DTOs via the mapper.
 */
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // GET /api/tasks or GET /api/tasks?projectId=1
    @GetMapping
    public List<TaskResponse> getTasks(@RequestParam(required = false) Long projectId) {
        List<Task> tasks;
        if (projectId != null) {
            tasks = taskService.findByProjectId(projectId);
        } else {
            tasks = taskService.findAll();
        }

        return TaskMapper.toResponseList(tasks);
    }

    // GET /api/tasks/{id}
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return TaskMapper.toResponse(task);
    }

    // POST /api/tasks -- Uses toModel() to convert DTO to model
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = TaskMapper.toModel(request);
        Task created = taskService.create(task);
        TaskResponse response = TaskMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /api/tasks/{id} -- Full update via toModel()
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                                   @Valid @RequestBody UpdateTaskRequest request) {
        Task updates = TaskMapper.toModel(request);
        Task task = taskService.fullUpdate(id, updates);
        return TaskMapper.toResponse(task);
    }

    // PATCH /api/tasks/{id} -- Partial update via toModel()
    @PatchMapping("/{id}")
    public TaskResponse patchTask(@PathVariable Long id,
                                  @Valid @RequestBody UpdateTaskRequest request) {
        Task updates = TaskMapper.toModel(request);
        Task task = taskService.partialUpdate(id, updates);
        return TaskMapper.toResponse(task);
    }

    // DELETE /api/tasks/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
