package com.ironhack.ironboard.service;

import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// =============================================
// TASK SERVICE — STEP 04: FULL CRUD
// =============================================
// CHANGED: Added fullUpdate(), partialUpdate(),
// and delete() methods.
//
// PATTERN: Same fullUpdate/partialUpdate/delete pattern
// as ProjectService. fullUpdate() sets fields unconditionally
// (PUT), partialUpdate() uses null checks (PATCH).
//
// =============================================
@Service
public class TaskService {

    // In-memory storage: maps task ID to Task object
    private final Map<Long, Task> tasks = new HashMap<>();

    // Simple counter for generating unique IDs
    private Long nextId = 1L;

    // Dependency on another service — injected via constructor
    private final ProjectService projectService;

    // Constructor injection — Spring provides the ProjectService bean automatically
    public TaskService(ProjectService projectService) {
        this.projectService = projectService;

        // Seed some tasks so we have data to work with
        Task t1 = new Task();
        t1.setId(nextId++);
        t1.setTitle("Set up Spring Boot project");
        t1.setDescription("Initialize the project with Spring Initializr");
        t1.setStatus(TaskStatus.DONE);
        t1.setType("FEATURE");
        t1.setProjectId(1L);
        tasks.put(t1.getId(), t1);

        Task t2 = new Task();
        t2.setId(nextId++);
        t2.setTitle("Create REST endpoints");
        t2.setDescription("Implement GET and POST endpoints for projects and tasks");
        t2.setStatus(TaskStatus.IN_PROGRESS);
        t2.setType("FEATURE");
        t2.setProjectId(1L);
        tasks.put(t2.getId(), t2);

        Task t3 = new Task();
        t3.setId(nextId++);
        t3.setTitle("Add input validation");
        t3.setDescription("Use Jakarta Validation annotations on model fields");
        t3.setStatus(TaskStatus.TODO);
        t3.setType("FEATURE");
        t3.setProjectId(1L);
        tasks.put(t3.getId(), t3);
    }

    /**
     * Returns all tasks as a list.
     */
    public List<Task> findAll() {
        return new ArrayList<>(tasks.values());
    }

    // PATTERN: Lookup-or-throw — same pattern as ProjectService.findById
    /**
     * Looks up a task by ID.
     * Throws RuntimeException if the task does not exist.
     */
    public Task findById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new RuntimeException("Task not found with id: " + id);
        }
        return task;
    }

    // =============================================
    // PATTERN: Cross-service validation — before
    // creating a task, we verify the referenced
    // project actually exists by calling
    // projectService.findById(). If the project
    // doesn't exist, this throws before we create
    // the task.
    //
    // =============================================
    /**
     * Creates a new Task, assigns a unique ID, stores it, and returns the created object.
     * Verifies that the referenced project exists before creating the task.
     */
    public Task create(String title, String description, Long projectId, String type) {
        // Verify the project exists -- throws RuntimeException if not found
        projectService.findById(projectId);

        Task task = new Task();
        task.setId(nextId++);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.TODO);
        task.setType(type);
        task.setProjectId(projectId);
        tasks.put(task.getId(), task);
        return task;
    }

    // =============================================
    // Filtering a collection manually.
    // We iterate over all tasks and add matching ones
    // to a result list.
    // =============================================
    /**
     * Returns all tasks that belong to a specific project.
     */
    public List<Task> findByProjectId(Long projectId) {
        List<Task> result = new ArrayList<>();
        for (Task task : tasks.values()) {
            if (task.getProjectId().equals(projectId)) {
                result.add(task);
            }
        }
        return result;
    }

    // =============================================
    // NEW: FULL UPDATE — FOR PUT
    // =============================================
    // PATTERN: fullUpdate() replaces title, description, and type
    // unconditionally. This matches PUT semantics.
    //
    // WHY these fields: When creating a task (POST), status
    // defaults to TODO. PUT "recreates" the task with the
    // same fields as POST. Status transitions should go
    // through PATCH instead.
    // =============================================

    /**
     * Replaces title, description, and type of an existing task (PUT semantics).
     * Status is not changed — use partialUpdate() for that.
     *
     * @param id the task ID to update
     * @param title new title (replaces existing)
     * @param description new description (replaces existing, may be null)
     * @return the updated task
     * @throws RuntimeException if the task doesn't exist
     */
    public Task fullUpdate(Long id, String title, String description, String type) {
        Task task = findById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setType(type);
        return task;
    }

    // =============================================
    // NEW: PARTIAL UPDATE — FOR PATCH
    // =============================================
    // PATTERN: partialUpdate() only sets fields that
    // are non-null. This matches PATCH semantics.
    //
    // WHY PATCH is more flexible than PUT here:
    // PATCH can change status and type, which PUT
    // cannot. The client sends only what they want
    // to change.
    //
    // Example PATCH requests:
    //   {"status": "IN_PROGRESS"}  — only changes status
    //   {"title": "New Title"}     — only changes title
    //   {"status": "DONE", "type": "BUG"} — changes both
    // =============================================

    /**
     * Updates only the non-null fields of an existing task (PATCH semantics).
     * Null fields are left untouched.
     *
     * @param id the task ID to update
     * @param title new title (null = don't change)
     * @param description new description (null = don't change)
     * @param status new status (null = don't change)
     * @param type new type (null = don't change)
     * @return the updated task
     * @throws RuntimeException if the task doesn't exist
     */
    public Task partialUpdate(Long id, String title, String description, TaskStatus status, String type) {
        Task task = findById(id);

        if (title != null) {
            task.setTitle(title);
        }
        if (description != null) {
            task.setDescription(description);
        }
        if (status != null) {
            task.setStatus(status);
        }
        if (type != null) {
            task.setType(type);
        }

        return task;
    }

    // =============================================
    // NEW: DELETE METHOD
    // =============================================
    // PATTERN: Same verify-then-delete as ProjectService.
    // =============================================

    /**
     * Deletes a task from the HashMap.
     *
     * @param id the task ID to delete
     * @throws RuntimeException if the task doesn't exist
     */
    public void delete(Long id) {
        // Verify the task exists (throws RuntimeException if not)
        findById(id);
        tasks.remove(id);
    }
}
