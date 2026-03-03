package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.model.Project;
import com.ironhack.ironboard.service.ProjectService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// =============================================
// PROJECT CONTROLLER — STEP 02: DI & SERVICE LAYER
// =============================================
// CHANGED: Now delegates to ProjectService instead
// of returning hardcoded data.
//
// PATTERN: Constructor injection — the controller
// declares its dependency (ProjectService) as a
// constructor parameter. Spring automatically finds
// the ProjectService bean and passes it in.
//
// COMMON MISTAKE: Using `new ProjectService()` instead
// of constructor injection. This creates a SEPARATE
// instance that Spring does NOT manage. If ProjectService
// had its own dependencies (like a database repository),
// they would NOT be injected and you'd get
// NullPointerException on the first database call.
//
// =============================================
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    // PATTERN: Dependency declared as a final field
    // WHY final: Ensures the dependency cannot change after construction
    private final ProjectService projectService;

    // PATTERN: Constructor injection — Spring provides the ProjectService bean
    // TIP: No @Autowired needed when there is only one constructor
    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // GET /api/projects — delegates to the service layer
    // PATTERN: The controller does NOT contain business logic — it calls the service
    @GetMapping
    public List<Project> getProjects() {
        // WHY: Controller's job is HTTP only — receive request, call service, return response
        return projectService.findAll();
    }
}
