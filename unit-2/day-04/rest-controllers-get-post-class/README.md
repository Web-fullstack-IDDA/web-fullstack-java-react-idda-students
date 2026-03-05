# IronBoard — Step 03: GET & POST with Validation

Adds `@PathVariable`, `@RequestParam`, `@RequestBody`, `@Valid`, and a second domain entity (Task).

## Changes from Step 02

| Change | File | What's new |
|--------|------|------------|
| NEW | `model/Task.java` | POJO with id, title, description, status, type, projectId |
| NEW | `service/TaskService.java` | `@Service` with HashMap storage, cross-service validation via `ProjectService` |
| NEW | `controller/TaskController.java` | `@RestController` at `/api/tasks` — GET all, GET by ID, POST |
| CHANGED | `model/Project.java` | Added `@NotBlank` + `@Size` validation on name |
| CHANGED | `model/Task.java` | Added `@NotNull` on projectId, `@NotBlank` + `@Size` on title |
| CHANGED | `controller/ProjectController.java` | Added GET by ID (`@PathVariable`), name filter (`@RequestParam`), POST with `@Valid` |
| CHANGED | `service/ProjectService.java` | Added `findById` with lookup-or-throw |
| CHANGED | `pom.xml` | Added `spring-boot-starter-validation` |

## Key concepts

- `@PathVariable` extracts from URL path (`/api/projects/{id}`)
- `@RequestParam(required = false)` extracts optional query params
- `@RequestBody` deserializes JSON body into a Java object
- `@Valid` triggers Jakarta Validation before the method runs
- `@NotBlank` for Strings, `@NotNull` for Long/Integer
- `ResponseEntity.status(HttpStatus.CREATED)` returns 201

## Run

```bash
mvn spring-boot:run
# GET  http://localhost:8080/api/projects
# GET  http://localhost:8080/api/projects/1
# GET  http://localhost:8080/api/projects?name=Iron
# POST http://localhost:8080/api/projects  (JSON body)
# GET  http://localhost:8080/api/tasks
# POST http://localhost:8080/api/tasks     (JSON body)
```
