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
@ControllerAdvice
public class GlobalExceptionHandler {

    // =============================================
    // Handler 1: ResourceNotFoundException → 404 Not Found
    // =============================================

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
    // Handler 3: IllegalArgumentException → 400 Bad Request
    // =============================================
    // Catches IllegalArgumentException thrown when
    // the client sends an invalid enum value.
    //
    // Example: PATCH /api/tasks/1 with {"status": "INVALID"}
    //   1. Controller calls TaskMapper.toModel(request)
    //   2. Mapper calls TaskStatus.valueOf("INVALID")
    //   3. valueOf() throws IllegalArgumentException
    //   4. This handler catches it and returns 400
    //
    // NOTE: Without this handler, the IllegalArgumentException would
    // fall through to the catch-all handler and return 500 — wrong!
    // An invalid enum value is a client error (400), not a server error.
    // =============================================

    /**
     * Handles IllegalArgumentException (e.g., invalid enum values in request).
     * Returns 400 with the descriptive message set by the mapper.
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad Request",
                ex.getMessage()
        );
        return ResponseEntity.badRequest().body(error);
    }

    // =============================================
    // Handler 4: Catch-all Exception → 500 Internal Server Error
    // =============================================

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
