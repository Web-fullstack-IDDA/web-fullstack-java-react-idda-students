package com.ironhack.ironboard.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;

// =============================================
// PROJECT MODEL — STEP 03: ADDED VALIDATION
// =============================================
// POJO representing a project. Jackson uses getters
// to serialize to JSON. No-arg constructor required
// for deserialization.
//
// Validation annotations on the model ensure that
// incoming data is valid when used with @Valid on
// the controller parameter.
// =============================================
public class Project {

    private Long id;

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;

    private String description;
    private LocalDateTime createdAt;

    public Project() {
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
}
