// =============================================
// ResourceNotFoundException - Custom exception for missing resources
// =============================================

package com.ironhack.ironboard.exception;

public class ResourceNotFoundException extends RuntimeException {

    /**
     * Constructor with a custom message.
     *
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }

    /**
     * Convenience constructor that builds a standard "not found" message.
     * Example: "Project not found with id: 42"
     */
    public ResourceNotFoundException(String resourceName, Long id) {
        super(resourceName + " not found with id: " + id);
    }
}
