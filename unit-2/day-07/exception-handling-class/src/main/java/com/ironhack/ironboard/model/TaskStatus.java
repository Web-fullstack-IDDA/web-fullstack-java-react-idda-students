// =============================================
// TaskStatus - Enum for task states (unchanged from previous steps)
// =============================================
/**
 * TaskStatus - Full CRUD + Exceptions
 *
 * Enum representing the possible states of a task.
 * Using an enum instead of a String ensures only valid values are used.
 */
package com.ironhack.ironboard.model;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE
}
