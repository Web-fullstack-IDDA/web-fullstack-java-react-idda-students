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

// =============================================
// TASK CONTROLLER -- DTOs & Mappers
// =============================================
// Every write endpoint follows the same flow:
//   1. Request DTO -> TaskMapper.toModel(request) -> Task model
//   2. Task model -> service method -> updated Task model
//   3. Updated Task model -> TaskMapper.toResponse(task) -> TaskResponse DTO
// =============================================
@RestController
@RequestMapping("/api/tasks")
public class TaskController {

    private final TaskService taskService;

    // Constructor injection -- Spring provides the TaskService bean automatically
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    // =============================================
    // GET /api/tasks -- List all tasks (with optional projectId filter)
    // Return type is now List<TaskResponse>.
    //
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

    // GET /api/tasks/{id} -- Return type is now TaskResponse
    @GetMapping("/{id}")
    public TaskResponse getTaskById(@PathVariable Long id) {
        Task task = taskService.findById(id);
        return TaskMapper.toResponse(task);
    }

    // =============================================
    // POST /api/tasks -- Create a new task
    // Uses TaskMapper.toModel(CreateTaskRequest) to convert
    // the request DTO to a model, then passes it to the service.
    // =============================================
    @PostMapping
    public ResponseEntity<TaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        Task task = TaskMapper.toModel(request);
        Task created = taskService.create(task);
        TaskResponse response = TaskMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // =============================================
    // PUT /api/tasks/{id} -- Full update
    // Uses TaskMapper.toModel(UpdateTaskRequest) to convert
    // the request DTO to a model.
    // =============================================
    @PutMapping("/{id}")
    public TaskResponse updateTask(@PathVariable Long id,
                                   @Valid @RequestBody UpdateTaskRequest request) {
        Task updates = TaskMapper.toModel(request);
        Task task = taskService.fullUpdate(id, updates);
        return TaskMapper.toResponse(task);
    }

    // =============================================
    // PATCH /api/tasks/{id} -- Partial update
    // Same mapper pattern as PUT.
    //
    // NOTE: The String-to-enum conversion for status happens
    // inside TaskMapper.toModel(). The mapper calls
    // TaskStatus.valueOf(request.getStatus()) when status is non-null.
    // =============================================
    @PatchMapping("/{id}")
    public TaskResponse patchTask(@PathVariable Long id,
                                  @Valid @RequestBody UpdateTaskRequest request) {
        Task updates = TaskMapper.toModel(request);
        Task task = taskService.partialUpdate(id, updates);
        return TaskMapper.toResponse(task);
    }

    // DELETE /api/tasks/{id} -- No change (returns void with 204)
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
