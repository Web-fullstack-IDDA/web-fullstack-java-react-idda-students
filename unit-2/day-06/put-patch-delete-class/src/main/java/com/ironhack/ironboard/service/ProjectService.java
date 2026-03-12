package com.ironhack.ironboard.service;

import com.ironhack.ironboard.model.Project;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// =============================================
// PROJECT SERVICE — STEP 04: FULL CRUD
// =============================================
// CHANGED: Added fullUpdate(), partialUpdate(),
// delete(), and findByName() methods.
//
// NOTE: findById() still throws RuntimeException
// when a project is not found. 
// We will later replace RuntimeException with a custom
// exception and add a centralized exception handler.
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

    // =============================================
    // NEW: FIND BY NAME — MOVED FROM CONTROLLER
    // =============================================
    // The name filtering logic was inline
    // in the controller. Here we move it to the service
    // layer where business logic belongs.
    //
    // PATTERN: Case-insensitive "contains" search.
    // findByName("iron") matches "IronBoard" and
    // "IronLibrary". This is more user-friendly than
    // exact matching.
    // =============================================

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

    // PATTERN: Lookup-or-throw — a very common pattern
    // in service layers. If the entity is not found,
    // throw an exception so the controller doesn't
    // have to handle null.
    /**
     * Looks up a project by ID.
     * Throws RuntimeException if the project does not exist.
     */
    public Project findById(Long id) {
        Project project = projects.get(id);
        if (project == null) {
            throw new RuntimeException("Project not found with id: " + id);
        }
        return project;
    }

    // PATTERN: The service builds the entity, assigns
    // server-generated fields (id, createdAt), persists
    // it, and returns the complete object.
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
    // NEW: FULL UPDATE — FOR PUT
    // =============================================
    // PATTERN: fullUpdate() replaces ALL fields
    // unconditionally. This matches PUT semantics:
    // the client sends the complete resource, and
    // every field is overwritten — even if a value
    // is null.
    //
    // WHY no null checks: PUT means "replace the
    // entire resource". If the client sends
    // {"name": "New", "description": null}, the
    // description should become null — the client
    // intentionally omitted it.
    // =============================================

    /**
     * Replaces all fields of an existing project (PUT semantics).
     * Every field is overwritten, even if the new value is null.
     *
     * @param id the project ID to update
     * @param name new name (replaces existing)
     * @param description new description (replaces existing, may be null)
     * @return the updated project
     * @throws RuntimeException if the project doesn't exist
     */
    public Project fullUpdate(Long id, String name, String description) {
        Project project = findById(id);
        project.setName(name);
        project.setDescription(description);
        return project;
    }

    // =============================================
    // NEW: PARTIAL UPDATE — FOR PATCH
    // =============================================
    // PATTERN: partialUpdate() only sets fields that
    // are non-null. This matches PATCH semantics:
    // the client sends only the fields they want to
    // change, and everything else stays the same.
    //
    // WHY null checks: If a field is null, it means
    // "the client didn't send this field". We skip
    // the setter to keep the existing value.
    // =============================================

    /**
     * Updates only the non-null fields of an existing project (PATCH semantics).
     * Null fields are left untouched.
     *
     * @param id the project ID to update
     * @param name new name (null = don't change)
     * @param description new description (null = don't change)
     * @return the updated project
     * @throws RuntimeException if the project doesn't exist
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
    // NEW: DELETE METHOD
    // =============================================
    // PATTERN: Verify-then-delete. We call findById()
    // first to ensure the resource exists. If it
    // doesn't, findById() throws RuntimeException.
    //
    // WHY verify first: Without the check, deleting a
    // non-existent ID would silently succeed (HashMap.remove
    // returns null for missing keys). The client would
    // get 204 No Content and think it worked, even
    // though nothing was deleted. This hides bugs.
    // =============================================

    /**
     * Deletes a project from the HashMap.
     *
     * @param id the project ID to delete
     * @throws RuntimeException if the project doesn't exist
     */
    public void delete(Long id) {
        // Verify the project exists (throws RuntimeException if not)
        findById(id);
        projects.remove(id);
    }
}
