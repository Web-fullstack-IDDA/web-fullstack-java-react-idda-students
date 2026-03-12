package com.ironhack.ironboard.exception;

import java.time.LocalDateTime;

// =============================================
// ErrorResponse — Consistent error response body
// =============================================
// WHY: Instead of using Map<String, Object> (loosely typed, easy to
// get wrong), we use a dedicated ErrorResponse class. This ensures
// EVERY error response has the exact same structure:
//   {
//     "status": 404,
//     "error": "Not Found",
//     "message": "Project not found with id: 42",
//     "timestamp": "2026-02-07T10:30:00"
//   }
//
// NOTE: This class has getters but no setters — it's effectively
// immutable after construction. The error response is built once
// and returned; there's no reason to modify it after creation.
// =============================================
public class ErrorResponse {

    private int status;
    private String error;
    private String message;
    private LocalDateTime timestamp;

    public ErrorResponse(int status, String error, String message) {
        this.status = status;
        this.error = error;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }

    // Getters only — ErrorResponse is immutable after construction

    public int getStatus() {
        return status;
    }

    public String getError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }
}
