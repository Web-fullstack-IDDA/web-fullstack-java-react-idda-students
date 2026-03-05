package com.ironhack.ironboard.service;

import com.ironhack.ironboard.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// =============================================
// PROJECT SERVICE — STEP 03: GET & POST + VALIDATION
// =============================================
// CHANGED from Step 02: Added findById(Long) and
// create(String, String) methods.
//
// - findById: looks up a project by ID, throws
//   RuntimeException if not found. (Custom exceptions
//   will replace RuntimeException in Step 04.)
// - create: builds a new Project object, assigns an
//   ID, and stores it.
//
// =============================================
@Service
public class ProjectService {

    // In-memory storage: maps project ID to Project object
    // NOTE: This will be replaced by a JPA repository in Step 06
    private final Map<Long, Project> projects = new HashMap<>();

    // Simple counter for generating unique IDs
    // NOTE: In Step 06, the database will auto-generate IDs
    private Long nextId = 1L;

    // Constructor: pre-load some seed data so we have something to see
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

    // NEW: Find a project by its ID
    // =============================================
    // PATTERN: Lookup-or-throw — a very common pattern
    // in service layers. If the entity is not found,
    // throw an exception so the controller doesn't
    // have to handle null.
    //
    // =============================================
    /**
     * Looks up a project by ID.
     * Throws RuntimeException if the project does not exist.
     * In Step 04, this will throw a custom ResourceNotFoundException instead.
     */
    public Project findById(Long id) {
        Project project = projects.get(id);
        if (project == null) {
            throw new RuntimeException("Project not found with id: " + id);
        }
        return project;
    }

    // NEW: Create a new project from the provided name and description
    // =============================================
    // PATTERN: The service builds the entity, assigns
    // server-generated fields (id, createdAt), persists
    // it, and returns the complete object.
    //
    // WHY return the created object: The client needs
    // to know the server-assigned id and createdAt.
    // Returning the full object avoids a second GET call.
    //
    // =============================================
    /**
     * Creates a new Project, assigns a unique ID and timestamp, stores it,
     * and returns the created object.
     */
    public Project create(String name, String description) {
        Project project = new Project();
        project.setId(nextId++);
        project.setName(name);
        project.setDescription(description);
        project.setCreatedAt(LocalDateTime.now());
        projects.put(project.getId(), project);
        return project;
    }
}
