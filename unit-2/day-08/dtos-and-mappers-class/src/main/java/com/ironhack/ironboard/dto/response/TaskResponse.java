package com.ironhack.ironboard.dto.response;

// =============================================
// TASK RESPONSE DTO — DTOs & Mappers
// =============================================
// NEW: Response DTO for Task — defines what the API returns.
//
// WHY: Same reasons as ProjectResponse (see that class).
// The key additional insight here is the STATUS field:
//
// NOTE: status is a String here, not the TaskStatus enum.
// WHY: In the domain model, status is TaskStatus (an enum).
// But in the API response, it becomes a plain String like
// "TODO", "IN_PROGRESS", or "DONE".
//
// This is intentional:
// 1. JSON doesn't have enums — it only has strings.
//    Returning an enum directly works (Jackson converts it),
//    but you lose control over the serialized format.
// 2. If you rename an enum value later (e.g., TODO -> PENDING),
//    you can keep returning "TODO" in the response DTO to
//    avoid breaking clients.
// 3. The mapper handles the conversion: task.getStatus().name()
//
// PATTERN: Response DTOs should be IMMUTABLE (final fields, all-args
// constructor, getters only). Jackson only needs to SERIALIZE
// them (calls getters), so no-arg constructor is NOT needed.
// =============================================
public class TaskResponse {

    private final Long id;
    private final String title;
    private final String description;
    // String and not TaskStatus: See class header comment above
    private final String status;
    private final String type;
    private final Long projectId;

    public TaskResponse(Long id, String title, String description, String status, String type, Long projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = status;
        this.type = type;
        this.projectId = projectId;
    }

    // Getters only — response DTOs are immutable

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public String getType() {
        return type;
    }

    public Long getProjectId() {
        return projectId;
    }
}
