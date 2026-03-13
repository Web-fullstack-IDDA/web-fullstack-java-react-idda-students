package com.ironhack.ironboard.mapper;

import com.ironhack.ironboard.dto.request.CreateTaskRequest;
import com.ironhack.ironboard.dto.request.UpdateTaskRequest;
import com.ironhack.ironboard.dto.response.TaskResponse;
import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.model.TaskStatus;

import java.util.ArrayList;
import java.util.List;

/**
 * Static utility class that converts between Task models and DTOs.
 */
public class TaskMapper {

    private TaskMapper() {
    }

    /**
     * Converts a CreateTaskRequest DTO to a Task model.
     * Internal fields (id, status) are set by the service, not here.
     */
    public static Task toModel(CreateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setType(request.getType());
        task.setProjectId(request.getProjectId());
        return task;
    }

    /**
     * Converts an UpdateTaskRequest DTO to a Task model.
     * Used by both PUT (full update) and PATCH (partial update).
     * Converts the status String to a TaskStatus enum if present.
     * Throws IllegalArgumentException if the status is invalid.
     */
    public static Task toModel(UpdateTaskRequest request) {
        Task task = new Task();
        task.setTitle(request.getTitle());
        task.setDescription(request.getDescription());
        task.setType(request.getType());
        if (request.getStatus() != null) {
            try {
                task.setStatus(TaskStatus.valueOf(request.getStatus()));
            } catch (IllegalArgumentException e) {
                throw new IllegalArgumentException(
                        "Invalid status: '" + request.getStatus() + "'. Must be one of: TODO, IN_PROGRESS, DONE");
            }
        }
        return task;
    }

    /**
     * Converts a Task model to a TaskResponse DTO.
     */
    public static TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus().name(),
                task.getType(),
                task.getProjectId()
        );
    }

    /**
     * Converts a list of Task models to TaskResponse DTOs.
     */
    public static List<TaskResponse> toResponseList(List<Task> tasks) {
        List<TaskResponse> result = new ArrayList<>();
        for (Task task : tasks) {
            result.add(toResponse(task));
        }
        return result;
    }
}
