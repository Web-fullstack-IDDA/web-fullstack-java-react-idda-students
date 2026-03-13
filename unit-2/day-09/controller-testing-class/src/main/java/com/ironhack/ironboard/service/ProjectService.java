package com.ironhack.ironboard.service;

import com.ironhack.ironboard.exception.ResourceNotFoundException;
import com.ironhack.ironboard.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Service layer for project business logic. Uses an in-memory HashMap for storage.
@Service
public class ProjectService {

    private final Map<Long, Project> projects = new HashMap<>();
    private Long nextId = 1L;

    // Pre-load seed data so we have something to work with
    public ProjectService() {
        Project p1 = new Project();
        p1.setId(nextId++);
        p1.setName("IronBoard");
        p1.setDescription("A project management application");
        p1.setCreatedAt(LocalDateTime.now());
        projects.put(p1.getId(), p1);

        Project p2 = new Project();
        p2.setId(nextId++);
        p2.setName("IronLibrary");
        p2.setDescription("A library management system");
        p2.setCreatedAt(LocalDateTime.now());
        projects.put(p2.getId(), p2);
    }

    /**
     * Returns all projects as a list.
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

    /**
     * Replaces all fields of an existing project (PUT semantics).
     * Every field is overwritten, even if the new value is null.
     */
    public Project fullUpdate(Long id, Project updates) {
        Project project = findById(id);
        project.setName(updates.getName());
        project.setDescription(updates.getDescription());
        return project;
    }

    /**
     * Updates only the non-null fields of an existing project (PATCH semantics).
     * Null fields are left untouched.
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

    /**
     * Deletes a project from the HashMap.
     */
    public void delete(Long id) {
        findById(id);
        projects.remove(id);
    }
}
