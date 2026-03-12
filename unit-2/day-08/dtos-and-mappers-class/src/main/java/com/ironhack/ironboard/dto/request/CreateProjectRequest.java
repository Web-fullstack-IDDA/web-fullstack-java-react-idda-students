package com.ironhack.ironboard.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

// =============================================
// CREATE PROJECT REQUEST DTO — Inbound DTO for POST /api/projects
// =============================================
// Request DTOs define what the CLIENT SENDS
// to the server. The server generates id and createdAt.
//
// WHY: Request DTOs only contain fields the client should
// provide. Compare with ProjectResponse (response DTO):
//   - Request DTO: name, description (client sends)
//   - Response DTO: id, name, description, createdAt (server returns)
// =============================================
public class CreateProjectRequest {

    @NotBlank(message = "Project name is required")
    @Size(min = 2, max = 100, message = "Project name must be between 2 and 100 characters")
    private String name;

    @Size(max = 500, message = "Description must be at most 500 characters")
    private String description;

    // No-arg constructor
    public CreateProjectRequest() {
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
