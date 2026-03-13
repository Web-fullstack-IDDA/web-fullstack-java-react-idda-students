package com.ironhack.ironboard.dto.response;

/**
 * Lightweight immutable response DTO for listing projects (GET /api/projects).
 * Only includes id and name -- detail fields are in ProjectResponse.
 */
public class ProjectSummary {

    private final Long id;
    private final String name;

    public ProjectSummary(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
