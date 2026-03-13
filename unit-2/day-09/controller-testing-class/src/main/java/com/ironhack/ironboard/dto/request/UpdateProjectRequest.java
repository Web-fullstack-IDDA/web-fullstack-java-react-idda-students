package com.ironhack.ironboard.dto.request;

import jakarta.validation.constraints.Size;

/**
 * Request DTO for updating a project (PUT/PATCH /api/projects/{id}).
 * Fields are optional — @Size only (no @NotBlank) for PATCH compatibility.
 */
public class UpdateProjectRequest {

    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    public UpdateProjectRequest() {
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
}
