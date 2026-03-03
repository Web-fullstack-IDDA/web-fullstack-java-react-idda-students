package com.ironhack.ironboard.service;

import com.ironhack.ironboard.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// =============================================
// PROJECT SERVICE — BUSINESS LOGIC LAYER
// =============================================
// Service class that contains business logic for projects.
// Uses a HashMap as in-memory storage
//
// PATTERN: @Service marks this class as a Spring-managed
// bean. Spring creates ONE instance at startup and injects
// it wherever it's needed (e.g., into ProjectController).
//
// WHY @Service and not just a regular class: Without
// @Service (or @Component), Spring does not know about
// this class.
//
// TIP: Think of @Service as telling Spring: "I exist,
// create me, and manage my lifecycle."
// =============================================

// PATTERN: @Service tells Spring to create one instance of this class and manage it
@Service
public class ProjectService {

    // In-memory storage: maps project ID to Project object
    private final Map<Long, Project> projects = new HashMap<>();

    // Simple counter for generating unique IDs
    private Long nextId = 1L;

    // Constructor: pre-load seed data so we have projects to display
    public ProjectService() {
        // Seed project 1
        Project p1 = new Project();
        p1.setId(nextId++);
        p1.setName("IronBoard");
        p1.setDescription("A project management application");
        p1.setCreatedAt(LocalDateTime.now());
        projects.put(p1.getId(), p1);

        // Seed project 2
        Project p2 = new Project();
        p2.setId(nextId++);
        p2.setName("IronLibrary");
        p2.setDescription("A library management system");
        p2.setCreatedAt(LocalDateTime.now());
        projects.put(p2.getId(), p2);
    }

    // PATTERN: Service methods contain the actual logic.
    // The controller calls this method — it does NOT access
    // the HashMap directly.
    /**
     * Returns all projects as a list.
     * HashMap.values() returns a Collection, so we wrap it in an ArrayList.
     */
    public List<Project> findAll() {
        return new ArrayList<>(projects.values());
    }
}
