package com.ironhack.ironboard.dto.response;

// =============================================
// PROJECT SUMMARY DTO -- DTOs & Mappers
// =============================================
// NEW: Lightweight response DTO for list endpoints (GET /api/projects).
//
// WHY: When listing all projects, the client doesn't need every field.
// A list view typically shows just the name and ID (for linking).
// Fields like description and createdAt are only needed when viewing
// a single project's detail page.
//
// PATTERN: Multiple response DTOs per model:
//   - ProjectResponse:  full detail (GET /api/projects/{id})
//   - ProjectSummary:   lightweight (GET /api/projects)
// This gives you control over how much data each endpoint returns.
//
// TIP: Compare to ProjectResponse:
//   ProjectResponse has:  id, name, description, createdAt
//   ProjectSummary has:   id, name
// The mapper (ProjectMapper.toSummary()) handles the conversion.
//
// PATTERN: Immutable (final fields, all-args constructor, getters only).
// Same as ProjectResponse -- response DTOs should be immutable.
// =============================================
public class ProjectSummary {

    private final Long id;
    private final String name;

    public ProjectSummary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    // Getters only -- response DTOs are immutable

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
