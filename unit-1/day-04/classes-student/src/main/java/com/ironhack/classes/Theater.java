package com.ironhack.classes;

/**
 * Demonstrates encapsulation: private fields, public getters/setters with validation.
 */
public class Theater {

    private String name;
    private int capacity;

    // public → any class can call `new Theater(...)`.
    // No modifier → only classes in the same package (package-private).
    public Theater(String name, int capacity) {
        this.name = name;
        setCapacity(capacity);
    }

    // Getters — read-only access to private fields

    public String getName() {
        return name;
    }

    public int getCapacity() {
        return capacity;
    }

    // Setters — controlled write access with validation

    public void setName(String name) {
        if (name == null || name.isBlank()) {
            System.out.println("  [REJECTED] Name cannot be null or blank.");
            return;
        }
        this.name = name;
    }

    public void setCapacity(int capacity) {
        if (capacity <= 0) {
            System.out.println("  [REJECTED] Capacity must be positive. Got: " + capacity);
            return;
        }
        this.capacity = capacity;
    }
}
