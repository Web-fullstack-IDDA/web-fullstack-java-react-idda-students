package com.ironhack.ironboard.model;

// =============================================
// Task model — DTOs & Mappers
// =============================================
// CHANGED: Validation annotations (@NotBlank,
// @Size, @NotNull) have been REMOVED from this model class.
// Validation now lives on the request DTOs (CreateTaskRequest,
// UpdateTaskRequest).
//
// WHY: Same reason as Project -- the model is the domain object,
// validation is an HTTP-layer concern handled by request DTOs.
//
// NOTE: The default status (TaskStatus.TODO) is still set here
// because it's a domain concern -- new tasks start as TODO
// regardless of how they're created (HTTP, CLI, etc.).
// =============================================
public class Task {

    private Long id;
    private String title;
    private String description;

    // Default status for new tasks
    private TaskStatus status = TaskStatus.TODO;

    private String type;
    private Long projectId;

    public Task() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getProjectId() {
        return projectId;
    }

    public void setProjectId(Long projectId) {
        this.projectId = projectId;
    }
}
