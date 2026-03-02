package com.ironhack.ironboard.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// =============================================
// PROJECT CONTROLLER
// =============================================
// It handles HTTP requests for the /api/projects URL path.
//
// PATTERN: @RestController = @Controller + @ResponseBody
//   - @Controller: marks this class as a Spring
//     component that handles web requests
//   - @ResponseBody: tells Spring to serialize return
//     values directly to the HTTP response body (as JSON)
//     instead of looking for a view/template
//
// PATTERN: @RequestMapping("/api/projects") sets the
// BASE PATH for all endpoints in this controller.
// Every @GetMapping, @PostMapping, etc. builds on top
// of this path.
//
// WHY "/api/": Convention to separate API endpoints
// from potential frontend routes. Makes it clear
// these return JSON, not HTML.
//
// =============================================

// PATTERN: @RestController tells Spring this class handles HTTP requests and returns JSON
@RestController
// PATTERN: @RequestMapping sets the base path — all endpoints start with /api/projects
@RequestMapping("/api/projects")
public class ProjectController {

    // PATTERN: @GetMapping maps HTTP GET requests to this method.
    // Since @RequestMapping already defines "/api/projects",
    // this method handles: GET /api/projects
    //
    // WHY List<String>: Spring + Jackson automatically
    // converts the List to a JSON array.
    @GetMapping
    public List<String> getProjects() {
        // NOTE: For now, return a hardcoded list of strings.
        return List.of("IronBoard project coming soon!");
    }
}
