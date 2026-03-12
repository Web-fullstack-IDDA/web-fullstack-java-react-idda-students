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
// PROJECT SERVICE — DTOs & Mappers
// =============================================

@Service
public class ProjectService {

    // In-memory storage: maps project ID to Project object
    private final Map<Long, Project> projects = new HashMap<>();

    // Simple counter for generating unique IDs
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

    // =============================================
    // CHANGED: create() now receives a Project model
    // object instead of raw parameters. The mapper (ProjectMapper.toModel())
    // converts the request DTO to a model in the controller, then the
    // controller passes the model here.
    //
    // The service sets the INTERNAL fields that the client never provides:
    //   - id (auto-generated)
    //   - createdAt (server timestamp)
    // These fields exist on the model but NOT on the request DTO --
    // that's the whole point of having separate DTOs and models.
    // =============================================

    /**
     * Assigns an ID and timestamp to the project, stores it, and returns it.
     * The caller provides a Project with name and description already set.
     */
    public Project create(Project project) {
        project.setId(nextId++);
        project.setCreatedAt(LocalDateTime.now());
        projects.put(project.getId(), project);
        return project;
    }

    // =============================================
    // FULL UPDATE -- FOR PUT
    // =============================================
    // CHANGED: Receives a Project model object instead
    // of raw parameters. The mapper (ProjectMapper.toModel()) converts
    // the UpdateProjectRequest DTO to a model in the controller.
    //
    // PATTERN: fullUpdate() replaces ALL fields unconditionally.
    // This matches PUT semantics: the client sends the complete
    // resource, and every field is overwritten.
    //
    // =============================================

    /**
     * Replaces all fields of an existing project (PUT semantics).
     * Every field is overwritten, even if the new value is null.
     *
     * @param id the project ID to update
     * @param updates a Project model containing the new field values
     * @return the updated project
     * @throws ResourceNotFoundException if the project doesn't exist
     */
    public Project fullUpdate(Long id, Project updates) {
        Project project = findById(id);
        project.setName(updates.getName());
        project.setDescription(updates.getDescription());
        return project;
    }

    // =============================================
    // PARTIAL UPDATE -- FOR PATCH
    // =============================================
    // CHANGED: Same model-based signature as fullUpdate().
    //
    // PATTERN: partialUpdate() only sets fields that are non-null.
    // This matches PATCH semantics.
    //
    // TIP: Compare fullUpdate() vs partialUpdate():
    //   fullUpdate:    project.setName(updates.getName())          -- always sets
    //   partialUpdate: if (updates.getName() != null) { ... }     -- only if sent
    // =============================================

    /**
     * Updates only the non-null fields of an existing project (PATCH semantics).
     * Null fields are left untouched.
     *
     * @param id the project ID to update
     * @param updates a Project model containing the new field values (nulls = don't change)
     * @return the updated project
     * @throws ResourceNotFoundException if the project doesn't exist
     */
    public Project partialUpdate(Long id, Project updates) {
        Project project = findById(id);

        if (updates.getName() != null) {
            project.setName(updates.getName());
        }
        if (updates.getDescription() != null) {
            project.setDescription(updates.getDescription());
        }

        return project;
    }

    // =============================================
    // DELETE METHOD
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
