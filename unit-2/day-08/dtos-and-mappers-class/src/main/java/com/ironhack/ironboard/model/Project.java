package com.ironhack.ironboard.model;

// =============================================
// Project model — DTOs & Mappers
// =============================================
// CHANGED: Validation annotations (@NotBlank, @Size)
// have been REMOVED from this model class. Validation now lives on the
// request DTOs (CreateProjectRequest, UpdateProjectRequest).
//
// WHY: The model is the domain object -- it should represent the business
// data without being tied to the HTTP layer. Validation is an HTTP
// concern: "what data is the CLIENT allowed to send?" The model doesn't
// care about that; it just stores data.
//
// PATTERN: Models are clean POJOs. Request DTOs carry validation.
// =============================================

import java.time.LocalDateTime;

public class Project {

    private Long id;
    private String name;
    private String description;
    private LocalDateTime createdAt;

    public Project() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
