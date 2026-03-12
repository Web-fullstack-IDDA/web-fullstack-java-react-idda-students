# IronBoard — Step 04: Exception Handling

Builds on Step 03 (PUT/PATCH/DELETE). Adds centralized exception handling with `@ControllerAdvice`.

## Changes from Step 03 (Day 6)

| Change    | File                                | What's new                                                                                  |
| --------- | ----------------------------------- | ------------------------------------------------------------------------------------------- |
| UNCHANGED | `model/Project.java`                | Same model with `@NotBlank` + `@Size` validation on name                                    |
| UNCHANGED | `model/Task.java`                   | Same model with `@NotBlank` + `@Size` on title, `@NotNull` on projectId                     |
| UNCHANGED | `model/TaskStatus.java`             | Same enum (TODO, IN_PROGRESS, DONE)                                                         |
| CHANGED   | `controller/ProjectController.java` | Same CRUD endpoints; exceptions now propagate to GlobalExceptionHandler                     |
| CHANGED   | `controller/TaskController.java`    | Same CRUD endpoints; exceptions now propagate to GlobalExceptionHandler                     |
| CHANGED   | `service/ProjectService.java`       | `findById` throws `ResourceNotFoundException` instead of `RuntimeException`                 |
| CHANGED   | `service/TaskService.java`          | `findById` throws `ResourceNotFoundException` instead of `RuntimeException`                 |
| NEW       | `exception/ResourceNotFoundException.java` | Custom unchecked exception for missing resources                                       |
| NEW       | `exception/ErrorResponse.java`      | Structured error response DTO (status, error, message, timestamp)                           |
| NEW       | `exception/GlobalExceptionHandler.java` | `@ControllerAdvice` with handlers for 404, 400, 500                                     |

## How exceptions are handled

| Exception | HTTP Status | When |
|-----------|-------------|------|
| `ResourceNotFoundException` | 404 Not Found | `findById` with non-existent ID |
| `MethodArgumentNotValidException` | 400 Bad Request | `@Valid` fails on request body |
| `Exception` (catch-all) | 500 Internal Server Error | Unexpected errors |

## Key concepts (Day 7 focus)

- `@ControllerAdvice` makes a class a global exception interceptor for all controllers
- `@ExceptionHandler(SomeException.class)` maps an exception type to a handler method
- Custom exceptions extend `RuntimeException` (unchecked) — no `throws` in method signatures
- `ErrorResponse` DTO provides consistent JSON error format (status, error, message, timestamp)
- Services throw → controllers propagate (no try-catch) → `@ControllerAdvice` catches and formats

## Run

```bash
mvn spring-boot:run
# GET http://localhost:8080/api/projects/999  → 404 JSON error
# POST http://localhost:8080/api/projects with empty name → 400 validation error
# GET http://localhost:8080/api/projects?name=Iron → filtered results
```
