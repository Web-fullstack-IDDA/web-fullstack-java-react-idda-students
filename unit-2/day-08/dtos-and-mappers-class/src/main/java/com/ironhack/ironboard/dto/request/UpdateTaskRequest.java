package com.ironhack.ironboard.dto.request;

import jakarta.validation.constraints.Size;

// =============================================
// UPDATE TASK REQUEST DTO — Inbound DTO for PUT/PATCH /api/tasks/{id}
// =============================================
// Same approach as UpdateProjectRequest — @Size only,
// no @NotBlank. All fields are optional for PATCH compatibility.
//
// The status field is a String here, not TaskStatus enum.
// Request DTOs should be framework-agnostic. The mapper
// (TaskMapper.toModel()) converts the String to a TaskStatus enum
// before the service receives it. This keeps the DTO simple and
// the service unchanged.
// =============================================
public class UpdateTaskRequest {

    @Size(min = 2, max = 200, message = "Task title must be between 2 and 200 characters")
    private String title;

    @Size(max = 1000, message = "Description must be at most 1000 characters")
    private String description;

    private String status;

    private String type;

    // No-arg constructor
    public UpdateTaskRequest() {
    }

    // Getters and setters

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
