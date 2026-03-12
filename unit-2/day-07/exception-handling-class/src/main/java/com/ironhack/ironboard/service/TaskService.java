package com.ironhack.ironboard.service;

import com.ironhack.ironboard.exception.ResourceNotFoundException;
import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// =============================================
// TASK SERVICE — FULL CRUD + EXCEPTIONS
// =============================================
// CHANGED: findById() now throws ResourceNotFoundException instead of RuntimeException.
// =============================================
@Service
public class TaskService {

    // Maps task ID to Task object
    private final Map<Long, Task> tasks = new HashMap<>();

    // Counter for generating unique IDs
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

    // =============================================
    // CHANGED: ResourceNotFoundException instead of RuntimeException
    // =============================================

    /**
     * Looks up a task by ID.
     * Throws ResourceNotFoundException if the task does not exist.
     */
    public Task findById(Long id) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new ResourceNotFoundException("Task", id);
        }
        return task;
    }

    /**
     * Creates a new Task, assigns a unique ID, stores it, and returns the created object.
     * Verifies that the referenced project exists before creating the task.
     */
    public Task create(String title, String description, Long projectId, String type) {
        // Verify the project exists
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
    // FULL UPDATE — FOR PUT
    // =============================================
    // fullUpdate() replaces title, description, and type
    // unconditionally. Status is NOT changeable via PUT — use
    // PATCH for that.
    // =============================================

    /**
     * Replaces title, description, and type of an existing task (PUT semantics).
     * Status is not changed — use partialUpdate() for that.
     *
     * @param id the task ID to update
     * @param title new title (replaces existing)
     * @param description new description (replaces existing, may be null)
     * @return the updated task
     * @throws ResourceNotFoundException if the task doesn't exist
     */
    public Task fullUpdate(Long id, String title, String description, String type) {
        Task task = findById(id);
        task.setTitle(title);
        task.setDescription(description);
        task.setType(type);
        return task;
    }

    // =============================================
    // PARTIAL UPDATE — FOR PATCH
    // =============================================
    // partialUpdate() only sets non-null fields.
    // PATCH can change status and type, which PUT cannot.
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
     * @throws ResourceNotFoundException if the task doesn't exist
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
    // DELETE METHOD
    // =============================================
    // Same verify-then-delete as ProjectService.
    // findById() → ResourceNotFoundException → GlobalExceptionHandler → 404.
    // =============================================

    /**
     * Deletes a task from the HashMap.
     *
     * @param id the task ID to delete
     * @throws ResourceNotFoundException if the task doesn't exist
     */
    public void delete(Long id) {
        findById(id);
        tasks.remove(id);
    }
}
