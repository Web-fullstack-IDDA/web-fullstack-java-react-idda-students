package com.ironhack.ironboard.service;

import com.ironhack.ironboard.exception.ResourceNotFoundException;
import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// Service layer for task business logic. Uses an in-memory HashMap for storage.
@Service
public class TaskService {

    private final Map<Long, Task> tasks = new HashMap<>();
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

    /**
     * Assigns an ID and default status to the task, stores it, and returns it.
     * Verifies that the referenced project exists before creating the task.
     */
    public Task create(Task task) {
        // Verify the project exists
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

    /**
     * Replaces title, description, and type of an existing task (PUT semantics).
     * Status is not changed -- use partialUpdate() for that.
     */
    public Task fullUpdate(Long id, Task updates) {
        Task task = findById(id);
        task.setTitle(updates.getTitle());
        task.setDescription(updates.getDescription());
        task.setType(updates.getType());
        return task;
    }

    /**
     * Updates only the non-null fields of an existing task (PATCH semantics).
     * Null fields are left untouched.
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

    /**
     * Deletes a task from the HashMap.
     */
    public void delete(Long id) {
        findById(id);
        tasks.remove(id);
    }
}
