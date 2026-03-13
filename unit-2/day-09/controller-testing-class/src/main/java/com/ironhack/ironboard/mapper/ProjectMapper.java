package com.ironhack.ironboard.mapper;

import com.ironhack.ironboard.dto.request.CreateProjectRequest;
import com.ironhack.ironboard.dto.request.UpdateProjectRequest;
import com.ironhack.ironboard.dto.response.ProjectSummary;
import com.ironhack.ironboard.dto.response.ProjectResponse;
import com.ironhack.ironboard.model.Project;

import java.util.ArrayList;
import java.util.List;

/**
 * Static utility class that converts between Project models and DTOs.
 */
public class ProjectMapper {

    private ProjectMapper() {
    }

    /**
     * Converts a CreateProjectRequest DTO to a Project model.
     * Internal fields (id, createdAt) are set by the service, not here.
     */
    public static Project toModel(CreateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return project;
    }

    /**
     * Converts an UpdateProjectRequest DTO to a Project model.
     * Used by both PUT (full update) and PATCH (partial update).
     */
    public static Project toModel(UpdateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return project;
    }

    /**
     * Converts a Project model to a ProjectResponse DTO (full detail).
     */
    public static ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt()
        );
    }

    /**
     * Converts a Project model to a ProjectSummary DTO (lightweight, for lists).
     */
    public static ProjectSummary toSummary(Project project) {
        return new ProjectSummary(project.getId(), project.getName());
    }

    /**
     * Converts a list of Project models to ProjectSummary DTOs.
     */
    public static List<ProjectSummary> toSummaryList(List<Project> projects) {
        List<ProjectSummary> result = new ArrayList<>();
        for (Project project : projects) {
            result.add(toSummary(project));
        }
        return result;
    }
}
