# IronBoard — Step 04: PUT, PATCH & DELETE

Adds full CRUD operations to the IronBoard app. Builds on Step 03 (GET & POST).

## Changes from Step 03

| Change    | File                                | What's new                                                                                  |
| --------- | ----------------------------------- | ------------------------------------------------------------------------------------------- |
| UNCHANGED | `model/Project.java`                | Same model with `@NotBlank` + `@Size` validation on name                                    |
| UNCHANGED | `model/Task.java`                   | Same model with `@NotBlank` + `@Size` on title, `@NotNull` on projectId                     |
| UNCHANGED | `model/TaskStatus.java`             | Same enum (TODO, IN_PROGRESS, DONE)                                                         |
| CHANGED   | `controller/ProjectController.java` | Added `@PutMapping`, `@PatchMapping`, `@DeleteMapping`; name filter refactored to service   |
| CHANGED   | `controller/TaskController.java`    | Added PUT, PATCH (status), DELETE endpoints                                                 |
| CHANGED   | `service/ProjectService.java`       | Added `fullUpdate`, `partialUpdate`, `delete`, `findByName`; throws `RuntimeException`      |
| CHANGED   | `service/TaskService.java`          | Added `fullUpdate`, `partialUpdate`, `delete`; keeps ProjectService injection               |

## Key concepts (Day 6 focus)

- **PUT** = full replacement (all required fields must be sent, validated with `@Valid`)
- **PATCH** = partial update (only the fields being changed, no `@Valid`)
- **DELETE** returns `ResponseEntity.noContent().build()` (204, empty body)
- Validation annotations live on the model; `@Valid` activates them on POST/PUT but is omitted on PATCH
- Services throw `RuntimeException` for not-found errors (custom exceptions are Day 7)

## Run

```bash
mvn spring-boot:run
# PUT    http://localhost:8080/api/projects/1       (full update)
# PATCH  http://localhost:8080/api/projects/1       (partial update)
# DELETE http://localhost:8080/api/projects/1
# PUT    http://localhost:8080/api/tasks/1
# PATCH  http://localhost:8080/api/tasks/1          (partial update, e.g. status change)
# DELETE http://localhost:8080/api/tasks/1
```
