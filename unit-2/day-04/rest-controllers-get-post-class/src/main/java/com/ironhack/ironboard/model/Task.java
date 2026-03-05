package com.ironhack.ironboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

// =============================================
// TASK MODEL — SECOND DOMAIN ENTITY
// =============================================
// A task belongs to a project (tracked by projectId).
//
// PATTERN: Same POJO structure as Project — fields,
// no-arg constructor, getters and setters. Jackson
// serializes it the same way.
//
// Validation annotations ensure that incoming data
// is valid when used with @Valid on the controller
// parameter.
// =============================================
public class Task {

    private Long id;

    @NotBlank(message = "Task title is required")
    @Size(min = 2, max = 200, message = "Task title must be between 2 and 200 characters")
    private String title;

    private String description;
    private TaskStatus status = TaskStatus.TODO;
    private String type;

    @NotNull(message = "Project ID is required")
    private Long projectId;

    public Task() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public TaskStatus getStatus() { return status; }
    public void setStatus(TaskStatus status) { this.status = status; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public Long getProjectId() { return projectId; }
    public void setProjectId(Long projectId) { this.projectId = projectId; }
}
