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
// TASK SERVICE — DTOs & Mappers
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

    // =============================================
    // CHANGED: create() now receives a Task model object
    // instead of raw parameters. Same pattern as ProjectService.create().
    //
    // The service sets the INTERNAL fields:
    //   - id (auto-generated)
    //   - status (defaults to TODO -- new tasks always start as TODO)
    //
    // =============================================

    /**
     * Assigns an ID and default status to the task, stores it, and returns it.
     * Verifies that the referenced project exists before creating the task.
     */
    public Task create(Task task) {
        // Verify the project exists -- throws ResourceNotFoundException if not found
        projectService.findById(task.getProjectId());

        task.setId(nextId++);
        task.setStatus(TaskStatus.TODO);
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
    // FULL UPDATE -- FOR PUT
    // =============================================
    // CHANGED: Receives a Task model object instead
    // of raw parameters. The mapper (TaskMapper.toModel()) converts
    // the UpdateTaskRequest DTO to a model in the controller.
    //
    // PATTERN: fullUpdate() replaces title, description, and type
    // unconditionally. Status is NOT changeable via PUT -- use
    // PATCH for that.
    //
    // =============================================

    /**
     * Replaces title, description, and type of an existing task (PUT semantics).
     * Status is not changed -- use partialUpdate() for that.
     *
     * @param id the task ID to update
     * @param updates a Task model containing the new field values
     * @return the updated task
     * @throws ResourceNotFoundException if the task doesn't exist
     */
    public Task fullUpdate(Long id, Task updates) {
        Task task = findById(id);
        task.setTitle(updates.getTitle());
        task.setDescription(updates.getDescription());
        task.setType(updates.getType());
        return task;
    }

    // =============================================
    // PARTIAL UPDATE -- FOR PATCH
    // =============================================
    // CHANGED: Same model-based signature as fullUpdate().
    //
    // PATTERN: partialUpdate() only sets non-null fields.
    // PATCH can change status and type, which PUT cannot.
    // =============================================

    /**
     * Updates only the non-null fields of an existing task (PATCH semantics).
     * Null fields are left untouched.
     *
     * @param id the task ID to update
     * @param updates a Task model containing the new field values (nulls = don't change)
     * @return the updated task
     * @throws ResourceNotFoundException if the task doesn't exist
     */
    public Task partialUpdate(Long id, Task updates) {
        Task task = findById(id);

        if (updates.getTitle() != null) {
            task.setTitle(updates.getTitle());
        }
        if (updates.getDescription() != null) {
            task.setDescription(updates.getDescription());
        }
        if (updates.getStatus() != null) {
            task.setStatus(updates.getStatus());
        }
        if (updates.getType() != null) {
            task.setType(updates.getType());
        }

        return task;
    }

    // =============================================
    // DELETE METHOD
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
