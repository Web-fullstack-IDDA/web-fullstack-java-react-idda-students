# IronBoard — Step 01: Spring Boot Intro

This is the starting point of the IronBoard app. A minimal Spring Boot project with one REST controller.

## What's in this step

| File | Description |
|------|-------------|
| `IronboardApplication.java` | `@SpringBootApplication` entry point |
| `controller/ProjectController.java` | `@RestController` with `@GetMapping` returning `List<String>` |
| `application.properties` |  |

## Key concepts

- `@SpringBootApplication` = `@Configuration` + `@ComponentScan` + `@EnableAutoConfiguration`
- `@RestController` = `@Controller` + `@ResponseBody`
- `@GetMapping` maps HTTP GET to a method
- Spring auto-serializes return values to JSON via Jackson

## Run

```bash
mvn spring-boot:run
# GET http://localhost:8080/api/projects
```
