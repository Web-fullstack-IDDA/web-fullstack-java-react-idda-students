package com.ironhack.classes;

/**
 * Demonstrates creating objects from a class, setting fields, and calling methods.
 */
public class ClassBasicsDemo {

    public static void run() {

        // Create two separate Movie objects
        Movie movie1 = new Movie();
        Movie movie2 = new Movie();

        // Set fields on each object independently
        movie1.title = "Inception";
        movie1.genre = "Sci-Fi";
        movie1.durationMinutes = 148;
        movie1.rating = 8.8;

        movie2.title = "The Grand Budapest Hotel";
        movie2.genre = "Comedy";
        movie2.durationMinutes = 100;
        movie2.rating = 8.1;

        // Call methods on each object
        System.out.println(movie1.getSummary());
        System.out.println(movie2.getSummary());

        // Each object is independent
        System.out.println("Is '" + movie1.title + "' long? " + movie1.isLong());
        System.out.println("Is '" + movie2.title + "' long? " + movie2.isLong());

        // Default values for unset fields
        Movie movie3 = new Movie();
        System.out.println("Unset movie title: " + movie3.title);
        System.out.println("Unset movie duration: " + movie3.durationMinutes);
        System.out.println("Unset movie rating: " + movie3.rating);
    }
}
