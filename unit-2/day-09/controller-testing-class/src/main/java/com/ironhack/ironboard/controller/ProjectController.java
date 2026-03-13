package com.ironhack.ironboard.controller;

import com.ironhack.ironboard.dto.request.CreateProjectRequest;
import com.ironhack.ironboard.dto.request.UpdateProjectRequest;
import com.ironhack.ironboard.dto.response.ProjectSummary;
import com.ironhack.ironboard.dto.response.ProjectResponse;
import com.ironhack.ironboard.mapper.ProjectMapper;
import com.ironhack.ironboard.model.Project;
import com.ironhack.ironboard.service.ProjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * REST controller for project CRUD operations.
 * Accepts request DTOs and returns response DTOs via the mapper.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // GET /api/projects or GET /api/projects?name=Iron
    // Returns ProjectSummary (lightweight: id + name only)
    @GetMapping
    public List<ProjectSummary> getProjects(@RequestParam(required = false) String name) {
        List<Project> projects;
        if (name != null && !name.isBlank()) {
            projects = projectService.findByName(name);
        } else {
            projects = projectService.findAll();
        }

        return ProjectMapper.toSummaryList(projects);
    }

    // GET /api/projects/{id} -- Returns full detail (ProjectResponse)
    @GetMapping("/{id}")
    public ProjectResponse getProjectById(@PathVariable Long id) {
        Project project = projectService.findById(id);
        return ProjectMapper.toResponse(project);
    }

    // POST /api/projects -- Uses toModel() to convert DTO to model
    @PostMapping
    public ResponseEntity<ProjectResponse> createProject(@Valid @RequestBody CreateProjectRequest request) {
        Project project = ProjectMapper.toModel(request);
        Project created = projectService.create(project);
        ProjectResponse response = ProjectMapper.toResponse(created);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    // PUT /api/projects/{id} -- Full update via toModel()
    @PutMapping("/{id}")
    public ProjectResponse updateProject(@PathVariable Long id,
                                         @Valid @RequestBody UpdateProjectRequest request) {
        Project updates = ProjectMapper.toModel(request);
        Project project = projectService.fullUpdate(id, updates);
        return ProjectMapper.toResponse(project);
    }

    // PATCH /api/projects/{id} -- Partial update via toModel()
    @PatchMapping("/{id}")
    public ProjectResponse patchProject(@PathVariable Long id,
                                        @Valid @RequestBody UpdateProjectRequest request) {
        Project updates = ProjectMapper.toModel(request);
        Project project = projectService.partialUpdate(id, updates);
        return ProjectMapper.toResponse(project);
    }

    // DELETE /api/projects/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        projectService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
