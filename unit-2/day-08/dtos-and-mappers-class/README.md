# IronBoard - Day 8: DTOs & Mappers

Builds on Day 7 (Exception Handling). Adds request/response DTOs and mapper classes. Controllers now accept DTOs and return DTOs instead of models.

## How DTOs and Mappers work

```
                         REQUEST FLOW (inbound -- create)

  Client --JSON--> CreateProjectRequest --> ProjectMapper.toModel() --> Service --> Model
                   (mutable, @Valid)        converts DTO to model      sets id, createdAt


                         REQUEST FLOW (inbound -- update)

  Client --JSON--> UpdateProjectRequest --> ProjectMapper.toModel() --> Service --> Model
                   (mutable, @Valid)        same method, overloaded    applies changes


                         RESPONSE FLOW (outbound -- detail)

  Client <--JSON-- ProjectResponse <------ ProjectMapper.toResponse() <---- Model
                   (immutable)             converts model to DTO


                         RESPONSE FLOW (outbound -- list)

  Client <--JSON-- ProjectSummary <-- ProjectMapper.toSummary() <-- Model
                   (fewer fields)          lightweight conversion


                         ERROR FLOW (extended from Day 7)

  Service/Mapper throws --> exception propagates --> GlobalExceptionHandler --> ErrorResponse JSON
                            (no try-catch)          @ControllerAdvice          (404, 400, 500)

  NEW: Invalid enum values (e.g., {"status": "INVALID"}) now return 400 Bad Request
       instead of 500, via IllegalArgumentException handler in GlobalExceptionHandler.
```

## Changes from Day 7

| Change    | File                                       | What's new                                                       |
| --------- | ------------------------------------------ | ---------------------------------------------------------------- |
| NEW       | `dto/request/CreateProjectRequest.java`    | Request DTO for POST with `@NotBlank` + `@Size`                  |
| NEW       | `dto/request/UpdateProjectRequest.java`    | Request DTO for PUT/PATCH with `@Size` only (no `@NotBlank`)     |
| NEW       | `dto/request/CreateTaskRequest.java`       | Request DTO for POST with `@NotBlank`, `@NotNull`, `@Size`       |
| NEW       | `dto/request/UpdateTaskRequest.java`       | Request DTO for PUT/PATCH with `@Size` only, `String status`     |
| NEW       | `dto/response/ProjectResponse.java`        | Immutable response DTO (`final` fields, getters only)            |
| NEW       | `dto/response/ProjectSummary.java`    | Lightweight response DTO for list endpoints (id + name only)     |
| NEW       | `dto/response/TaskResponse.java`           | Immutable response DTO (`final` fields, `String status`)         |
| NEW       | `mapper/ProjectMapper.java`                | `toModel()` x2, `toResponse()`, `toSummary()`, `toSummaryList()` |
| NEW       | `mapper/TaskMapper.java`                   | `toModel()` x2, `toResponse()`, `toResponseList()` -- with enum conversion |
| CHANGED   | `model/Project.java`                       | Removed validation annotations (moved to request DTOs)           |
| CHANGED   | `model/Task.java`                          | Removed validation annotations (moved to request DTOs)           |
| CHANGED   | `controller/ProjectController.java`        | All endpoints use mappers (toModel for write, toResponse/toSummary for read) |
| CHANGED   | `controller/TaskController.java`           | All endpoints use mappers (toModel for write, toResponse for read) |
| CHANGED   | `service/ProjectService.java`              | `create()`, `fullUpdate()`, `partialUpdate()` receive model objects |
| CHANGED   | `service/TaskService.java`                 | `create()`, `fullUpdate()`, `partialUpdate()` receive model objects |
| CHANGED   | `exception/GlobalExceptionHandler.java`    | Added `IllegalArgumentException` handler → 400 (invalid enum values)  |
| UNCHANGED | `exception/ErrorResponse.java`             | Same as Day 7                                                         |
| UNCHANGED | `exception/ValidationErrorResponse.java`   | Same as Day 7                                                         |
| UNCHANGED | `exception/ResourceNotFoundException.java` | Same as Day 7                                                         |

## Key concepts (Day 8 focus)

- **Request DTOs** = what the client sends (mutable: no-arg constructor + setters for Jackson deserialization)
- **Response DTOs** = what the client receives (immutable: `final` fields, all-args constructor, getters only)
- **List DTOs** = lightweight response for list endpoints (fewer fields than full response)
- **Mappers** centralize conversion using method overloading:
  - `toModel(CreateRequest)` -- request DTO to model (inbound, for POST)
  - `toModel(UpdateRequest)` -- request DTO to model (inbound, for PUT/PATCH)
  - `toResponse()` -- model to full response DTO (outbound, for GET by id)
  - `toSummary()` -- model to lightweight DTO (outbound, single item)
  - `toSummaryList()` / `toResponseList()` -- list of models to list of DTOs (outbound, for GET all)
- Controllers never expose models directly -- always map to response DTOs
- Services accept model objects for all write operations -- never DTOs
- Validation annotations move from models to request DTOs
- `dto/request/` and `dto/response/` package separation

## Run

```bash
mvn spring-boot:run
# All previous endpoints still work, but now return DTO-shaped JSON
# GET  http://localhost:8080/api/projects            -> list of ProjectSummary (id + name)
# GET  http://localhost:8080/api/projects/1          -> ProjectResponse (full detail)
# GET  http://localhost:8080/api/projects?name=Iron  -> filtered list of ProjectSummary
# POST http://localhost:8080/api/projects            -> 201 with ProjectResponse
# GET  http://localhost:8080/api/projects/999        -> 404 ErrorResponse JSON
```
