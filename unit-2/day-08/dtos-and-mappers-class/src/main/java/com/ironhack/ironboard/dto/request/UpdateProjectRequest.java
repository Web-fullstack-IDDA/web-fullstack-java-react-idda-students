package com.ironhack.ironboard.dto.request;

import jakarta.validation.constraints.Size;

// =============================================
// UPDATE PROJECT REQUEST DTO — Inbound DTO for PUT/PATCH /api/projects/{id}
// =============================================
// Fields are optional — no @NotBlank, only @Size.
// This allows the same DTO to be used for both PUT and PATCH.
// =============================================
public class UpdateProjectRequest {

    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    // No-arg constructor
    public UpdateProjectRequest() {
    }

    // Getters and setters

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
}
