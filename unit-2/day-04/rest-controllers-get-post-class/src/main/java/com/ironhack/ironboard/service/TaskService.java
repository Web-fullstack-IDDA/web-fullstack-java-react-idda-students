package com.ironhack.ironboard.service;

import com.ironhack.ironboard.model.Task;
import com.ironhack.ironboard.model.TaskStatus;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// =============================================
// TASK SERVICE — STEP 03: GET & POST + VALIDATION
// =============================================
// NEW: Service class that contains business logic
// for tasks.
//
// PATTERN: Same service structure as ProjectService
// — @Service, HashMap storage, findAll/findById/create.
// Tasks are linked to projects via projectId.
//
// PATTERN: Constructor injection of ProjectService.
// TaskService depends on ProjectService to verify
// that a project exists before creating a task.
//
// TIP: Notice the chain of dependencies:
//   TaskController -> TaskService -> ProjectService
// Spring resolves the entire chain automatically
// when creating the beans.
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
    public Task create(String title, String description, Long projectId) {
        // Verify the project exists -- throws RuntimeException if not found
        projectService.findById(projectId);

        Task task = new Task();
        task.setId(nextId++);
        task.setTitle(title);
        task.setDescription(description);
        task.setStatus(TaskStatus.TODO);
        task.setProjectId(projectId);
        tasks.put(task.getId(), task);
        return task;
    }

    // =============================================
    // Filtering a collection manually.
    // We iterate over all tasks and add matching ones
    // to a result list.
    //
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
}
