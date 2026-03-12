// =============================================
// ResourceNotFoundException - Custom exception for missing resources
// =============================================
//
// WHY: We create a custom exception class so that our GlobalExceptionHandler
// can specifically catch "resource not found" scenarios and return a proper
// 404 HTTP response with a consistent JSON body. Without this, we'd either
// return null (which gives a confusing 200 with empty body) or use generic
// exceptions that don't convey the right HTTP semantics.
//
// =============================================
/**
 * ResourceNotFoundException - Full CRUD + Exceptions
 *
 * NEW: Custom exception thrown when a requested resource (Project, Task, etc.) is not found.
 * Extends RuntimeException so it does not need to be declared in method signatures.
 *
 * The GlobalExceptionHandler catches this exception and returns a 404 response
 * with a consistent JSON error format.
 */
package com.ironhack.ironboard.exception;

public class ResourceNotFoundException extends RuntimeException {

    // TIP: Two constructors give us flexibility in how we throw this exception.
    //   Constructor 1: Custom message for edge cases
    //     throw new ResourceNotFoundException("No active projects found")
    //   Constructor 2: Standard pattern for ID lookups (used 90% of the time)
    //     throw new ResourceNotFoundException("Project", id)

    /**
     * Constructor with a custom message.
     *
     */
    // WHY: super(message) passes the message to RuntimeException, which stores it.
    // Later, ex.getMessage() in the GlobalExceptionHandler retrieves it for the JSON response.
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Convenience constructor that builds a standard "not found" message.
     * Example: "Project not found with id: 42"
     *
     */
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }
}
