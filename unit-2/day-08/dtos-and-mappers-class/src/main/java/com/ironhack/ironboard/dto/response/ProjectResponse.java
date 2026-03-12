package com.ironhack.ironboard.dto.response;

import java.time.LocalDateTime;

// =============================================
// PROJECT RESPONSE DTO — DTOs & Mappers
// =============================================
// NEW: This is a Response DTO — it defines what the API
// RETURNS to the client.
//
// WHY: We never return the domain model (Project) directly.
// Reasons:
// 1. DECOUPLING: The internal model can change (add fields,
//    rename columns, add JPA annotations) without breaking
//    the API contract for clients.
// 2. SECURITY: You control exactly which fields the client
//    sees. Internal fields (passwords, internal flags) are
//    never accidentally exposed.
// 3. STABILITY: Frontend developers rely on a stable API.
//    If you return models directly, any model change is
//    a breaking change.
//
// PATTERN: Request DTOs vs Response DTOs
// - Request DTOs (CreateProjectRequest, UpdateProjectRequest):
//   Define what the CLIENT SENDS to the server.
//   Have validation annotations (@NotBlank, @Size).
//   Are MUTABLE (no-arg constructor + setters) because Jackson
//   needs to deserialize incoming JSON into them.
// - Response DTOs (ProjectResponse):
//   Define what the SERVER RETURNS to the client.
//   Have NO validation — they are output-only.
//   Are IMMUTABLE (final fields + all-args constructor + getters only)
//   because Jackson only needs to SERIALIZE them (calls getters).
//
// =============================================
public class ProjectResponse {

    private final Long id;
    private final String name;
    private final String description;
    private final LocalDateTime createdAt;

    public ProjectResponse(Long id, String name, String description, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = createdAt;
    }

    // Getters only — response DTOs are immutable

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}
