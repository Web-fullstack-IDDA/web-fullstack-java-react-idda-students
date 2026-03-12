package com.ironhack.ironboard.exception;

import java.time.LocalDateTime;
import java.util.List;

// =============================================
// ValidationErrorResponse — Error response for validation failures
// =============================================    
public class ValidationErrorResponse {

    private int status;
    private String error;
    private List<String> fieldErrors;
    private LocalDateTime timestamp;

    public ValidationErrorResponse(int status, String error, List<String> fieldErrors) {
        this.status = status;
        this.error = error;
        this.fieldErrors = fieldErrors;
        this.timestamp = LocalDateTime.now();
    }

    // Getters only — immutable after construction

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public List<String> getFieldErrors() {
        return fieldErrors;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
