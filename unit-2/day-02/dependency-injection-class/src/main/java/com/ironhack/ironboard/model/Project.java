package com.ironhack.ironboard.model;

import java.time.LocalDateTime;

// =============================================
// PROJECT MODEL — DATA REPRESENTATION
// =============================================
// A plain Java class (POJO) representing a project.
// This is NOT a JPA entity yet — just a class with
// fields, getters, and setters.
//
// PATTERN: Model classes hold data and expose it
// through getters and setters. They have no
// business logic — just data structure.
//
// WHY getters are required: When Spring returns
// this object from a controller, Jackson (the JSON
// library included in spring-boot-starter-webmvc)
// automatically converts it to JSON. Jackson looks
// for PUBLIC GETTERS to determine what fields to
// include in the JSON output.
//
// COMMON MISTAKE: Forgetting getters. If you don't
// have getters, Jackson produces an empty JSON object
// {} even though the fields have values.
//
// =============================================
public class Project {

    // Unique identifier — manually assigned in Step 02 (auto-generated in Step 06+)
    private Long id;

    // Project name (required in future steps via @NotBlank)
    private String name;

    // Optional description of the project
    private String description;

    // Timestamp when the project was created
    private LocalDateTime createdAt;

    // No-arg constructor — required for JSON deserialization and later JPA
    public Project() {
    }

    // --- GETTERS AND SETTERS ---
    // WHY: Jackson uses getters to serialize (Java → JSON)
    // and setters to deserialize (JSON → Java).

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
