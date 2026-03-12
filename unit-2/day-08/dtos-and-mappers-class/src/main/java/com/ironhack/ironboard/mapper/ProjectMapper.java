package com.ironhack.ironboard.mapper;

import com.ironhack.ironboard.dto.request.CreateProjectRequest;
import com.ironhack.ironboard.dto.request.UpdateProjectRequest;
import com.ironhack.ironboard.dto.response.ProjectSummary;
import com.ironhack.ironboard.dto.response.ProjectResponse;
import com.ironhack.ironboard.model.Project;

import java.util.ArrayList;
import java.util.List;

// =============================================
// PROJECT MAPPER -- DTOs & Mappers
// =============================================
// NEW: A Mapper converts between domain objects and DTOs.
//
// WHY a mapper class?
// Without a mapper, conversion code lives in the controller:
//   new ProjectResponse(project.getId(), project.getName(), ...);
// This clutters the controller and duplicates logic if
// multiple endpoints return the same DTO.
//
// With a mapper, the controller is clean:
//   return ProjectMapper.toResponse(project);
//
// PATTERN: Static utility class with private constructor.
// - All methods are static (no instance needed).
// - Private constructor prevents new ProjectMapper().
// - This is the simplest mapper pattern in Java.
//
// NOTE: In larger projects, you might use MapStruct
// (a code-generation library) to auto-generate mappers.
// For learning, manual mappers are better because you
// see exactly what happens.
//
// PATTERN: This mapper converts in FIVE directions:
//   1. toModel(CreateProjectRequest):  request DTO -> model  [inbound, create]
//   2. toModel(UpdateProjectRequest):  request DTO -> model  [inbound, update]
//   3. toResponse():  Project -> ProjectResponse             [outbound, detail]
//   4. toSummary():  Project -> ProjectSummary     [outbound, list item]
//   5. toSummaryList():  List<Project> -> List<ProjectSummary>  [outbound, list]
//
// =============================================
public class ProjectMapper {

    // PATTERN: Private constructor prevents instantiation
    private ProjectMapper() {
    }

    // =============================================
    // toModel(CreateProjectRequest) -- Inbound conversion for POST
    //
    // PATTERN: The mapper only sets the fields that the CLIENT provides.
    // Internal fields (id, createdAt) are NOT set here -- the service
    // handles those because they are domain concerns, not HTTP concerns.
    // =============================================
    public static Project toModel(CreateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return project;
    }

    // =============================================
    // toModel(UpdateProjectRequest) -- Inbound conversion for PUT/PATCH
    //
    // PATTERN: Same method name as above, different parameter type.
    // This is method overloading -- Java selects the right version
    // based on the argument type at compile time.
    // =============================================
    public static Project toModel(UpdateProjectRequest request) {
        Project project = new Project();
        project.setName(request.getName());
        project.setDescription(request.getDescription());
        return project;
    }

    // =============================================
    // toResponse() -- Converts a Project model to a ProjectResponse DTO
    //
    // PATTERN: Since ProjectResponse is immutable (final fields),
    // we pass all values through the constructor. This is cleaner
    // than creating an empty object and calling setters -- the
    // response is fully built in one step.
    // =============================================
    public static ProjectResponse toResponse(Project project) {
        return new ProjectResponse(
                project.getId(),
                project.getName(),
                project.getDescription(),
                project.getCreatedAt()
        );
    }

    // =============================================
    // toSummary() -- Converts a Project model to a ProjectSummary DTO
    //
    // PATTERN: Lightweight conversion for list endpoints.
    // Only includes id and name -- the client doesn't need
    // description or createdAt when browsing a list.
    // =============================================
    public static ProjectSummary toSummary(Project project) {
        return new ProjectSummary(project.getId(), project.getName());
    }

    // =============================================
    // toSummaryList() -- Converts a list of Project models to ProjectSummary DTOs
    //
    // PATTERN: Centralizes list conversion in the mapper instead
    // of the controller. The controller calls this single method
    // instead of writing its own for-loop.
    // =============================================
    public static List<ProjectSummary> toSummaryList(List<Project> projects) {
        List<ProjectSummary> result = new ArrayList<>();
        for (Project project : projects) {
            result.add(toSummary(project));
        }
        return result;
    }
}
