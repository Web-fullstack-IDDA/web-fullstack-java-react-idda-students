package com.ironhack.classes;

/**
 * Demonstrates encapsulation: private fields, getters, setters with validation.
 */
public class EncapsulationDemo {

    public static void run() {

        Theater theater = new Theater("Grand Cinema", 50);

        // Getters — read private fields
        System.out.println("Name: " + theater.getName());
        System.out.println("Capacity: " + theater.getCapacity());

        // Direct access is blocked:
        //   theater.capacity = -100;   // COMPILE ERROR
        //   theater.name = "";         // COMPILE ERROR

        // Setters reject invalid values
        System.out.println("\n-- Trying invalid values --");

        theater.setCapacity(-10);
        System.out.println("Capacity after bad set: " + theater.getCapacity());

        theater.setName("");
        System.out.println("Name after bad set: " + theater.getName());

        // Setters accept valid values
        System.out.println("\n-- Trying valid values --");

        theater.setCapacity(200);
        System.out.println("Capacity after good set: " + theater.getCapacity());

        theater.setName("IMAX Hall");
        System.out.println("Name after good set: " + theater.getName());
    }
}
