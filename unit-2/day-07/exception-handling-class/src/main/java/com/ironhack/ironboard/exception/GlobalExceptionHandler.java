package com.ironhack.ironboard.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.ArrayList;
import java.util.List;

// =============================================
// GlobalExceptionHandler — Centralized exception handling
// =============================================
// WHY: Without this class, every controller method would need its own
// try-catch blocks to handle exceptions and build error responses.
// That's repetitive and error-prone. @ControllerAdvice acts as a
// "catch net" that intercepts exceptions thrown from ANY controller
// and converts them to proper HTTP responses.
//
// PATTERN: @ControllerAdvice + @ExceptionHandler = centralized error handling.
//   The flow is:
//     1. Client sends request (e.g., GET /api/projects/999)
//     2. Controller calls service (projectService.findById(999))
//     3. Service throws ResourceNotFoundException("Project", 999)
//     4. Exception propagates OUT of the controller method (no try-catch needed)
//     5. Spring intercepts the exception BEFORE sending a response
//     6. Spring finds this @ControllerAdvice class
//     7. Spring matches the exception type to an @ExceptionHandler method
//     8. That method builds and returns the error response (404 JSON)
//
// =============================================
@ControllerAdvice
public class GlobalExceptionHandler {

    // =============================================
    // Handler 1: ResourceNotFoundException → 404 Not Found
    // =============================================
    // PATTERN: @ExceptionHandler(SomeException.class) tells Spring:
    //   "When SomeException is thrown anywhere in a controller, call THIS method."


    /**
     * Handles ResourceNotFoundException.
     * Returned when a requested resource does not exist (e.g., GET /api/projects/999).
     */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // =============================================
    // Handler 2: MethodArgumentNotValidException → 400 Bad Request
    // =============================================
    // WHY: This exception is thrown automatically by Spring when @Valid fails.
    //   Example: POST /api/projects with body {"name": ""} → @NotBlank fails.
    //   Spring validates BEFORE the controller method body runs, so the controller
    //   never sees the invalid data.

    /**
     * Handles validation errors from @Valid on request bodies.
     * Collects all field-level errors into a list for clear, field-specific feedback.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ValidationErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
        List<String> fieldErrors = new ArrayList<>();
        for (var error : ex.getBindingResult().getFieldErrors()) {
            fieldErrors.add(error.getField() + ": " + error.getDefaultMessage());
        }

        ValidationErrorResponse response = new ValidationErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Failed",
                fieldErrors
        );
        return ResponseEntity.badRequest().body(response);
    }

    // =============================================
    // Handler 3: Catch-all Exception → 500 Internal Server Error
    // =============================================
    // WHY: This is the safety net. If any unexpected exception occurs (e.g.,
    //   NullPointerException, database connection error, etc.), this handler
    //   catches it and returns a generic 500 error.

    /**
     * Catch-all handler for any unexpected exceptions.
     * Returns a generic 500 error — never expose internal details to clients.
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGenericException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "Internal Server Error",
                "An unexpected error occurred"
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
