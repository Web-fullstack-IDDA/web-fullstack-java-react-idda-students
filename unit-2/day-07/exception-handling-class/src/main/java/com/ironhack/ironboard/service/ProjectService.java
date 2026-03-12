package com.ironhack.ironboard.service;

import com.ironhack.ironboard.exception.ResourceNotFoundException;
import com.ironhack.ironboard.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// =============================================
// PROJECT SERVICE — FULL CRUD + EXCEPTIONS
// =============================================
// CHANGED: findById() now throws ResourceNotFoundException instead of RuntimeException.
// =============================================
@Service
public class ProjectService {

    // Maps project ID to Project object
    private final Map<Long, Project> projects = new HashMap<>();

    // Counter for generating unique IDs
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

    /**
     * Returns all projects as a list.
     * HashMap.values() returns a Collection, so we wrap it in an ArrayList.
     */
    public List<Project> findAll() {
        return new ArrayList<>(projects.values());
    }

    /**
     * Returns projects whose name contains the given string (case-insensitive).
     */
    public List<Project> findByName(String name) {
        List<Project> result = new ArrayList<>();
        String lowerName = name.toLowerCase();
        for (Project p : projects.values()) {
            if (p.getName().toLowerCase().contains(lowerName)) {
                result.add(p);
            }
        }
        return result;
    }

    // =============================================
    // CHANGED: ResourceNotFoundException instead of RuntimeException
    // =============================================
    // WHY: This is the KEY change for exception handling.
    // Throws ResourceNotFoundException
    //   → GlobalExceptionHandler catches it → returns 404 Not Found
    // =============================================

    /**
     * Looks up a project by ID.
     * Throws ResourceNotFoundException if the project does not exist.
     */
    public Project findById(Long id) {
        Project project = projects.get(id);
        if (project == null) {
            throw new ResourceNotFoundException("Project", id);
        }
        return project;
    }

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

    // =============================================
    // FULL UPDATE — FOR PUT
    // =============================================
    // fullUpdate() replaces ALL fields unconditionally.
    // =============================================

    /**
     * Replaces all fields of an existing project (PUT semantics).
     * Every field is overwritten, even if the new value is null.
     *
     * @param id the project ID to update
     * @param name new name (replaces existing)
     * @param description new description (replaces existing, may be null)
     * @return the updated project
     * @throws ResourceNotFoundException if the project doesn't exist
     */
    public Project fullUpdate(Long id, String name, String description) {
        Project project = findById(id);
        project.setName(name);
        project.setDescription(description);
        return project;
    }

    // =============================================
    // PARTIAL UPDATE — FOR PATCH
    // =============================================
    // partialUpdate() only sets fields that are non-null.
    // This matches PATCH semantics.
    // =============================================

    /**
     * Updates only the non-null fields of an existing project (PATCH semantics).
     * Null fields are left untouched.
     *
     * @param id the project ID to update
     * @param name new name (null = don't change)
     * @param description new description (null = don't change)
     * @return the updated project
     * @throws ResourceNotFoundException if the project doesn't exist
     */
    public Project partialUpdate(Long id, String name, String description) {
        Project project = findById(id);

        if (name != null) {
            project.setName(name);
        }
        if (description != null) {
            project.setDescription(description);
        }

        return project;
    }   

    // =============================================
    // DELETE METHOD
    // =============================================
    // Verify-then-delete. We call findById() first to ensure
    // the resource exists. If it doesn't, findById() throws
    // ResourceNotFoundException → GlobalExceptionHandler returns 404.
    // =============================================

    /**
     * Deletes a project from the HashMap.
     *
     * @param id the project ID to delete
     * @throws ResourceNotFoundException if the project doesn't exist
     */
    public void delete(Long id) {
        findById(id);
        projects.remove(id);
    }
}
