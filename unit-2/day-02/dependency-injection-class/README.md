# IronBoard — Step 02: Dependency Injection & Service Layer

Introduces the 3-layer architecture: Controller → Service → Data. The controller no longer holds data — it delegates to a service.

## Changes from Step 01

| Change | File | What's new |
|--------|------|------------|
| NEW | `model/Project.java` | POJO with id, name, description, createdAt |
| NEW | `model/TaskStatus.java` | Enum: TODO, IN_PROGRESS, DONE |
| NEW | `service/ProjectService.java` | `@Service` with HashMap storage, findAll/findById/create |
| CHANGED | `controller/ProjectController.java` | Now injects `ProjectService` via constructor, returns `List<Project>` instead of `List<String>` |

## Key concepts

- `@Service` registers a class as a Spring-managed bean
- Constructor injection: `final` field + single constructor (no `@Autowired` needed)
- Model classes are plain POJOs — no Spring annotations
- Controller delegates all logic to the service layer

## Run

```bash
mvn spring-boot:run
# GET http://localhost:8080/api/projects
```
