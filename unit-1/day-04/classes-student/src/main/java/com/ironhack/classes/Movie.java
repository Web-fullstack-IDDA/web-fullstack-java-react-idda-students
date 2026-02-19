package com.ironhack.classes;

/**
 * A class is a blueprint for creating objects. Movie defines fields (state) and methods (behavior).
 */
public class Movie {

    String title;
    String genre;
    int durationMinutes;
    double rating;

    String getSummary() {
        return title + " (" + genre + ") - " + durationMinutes + " min, Rating: " + rating;
    }

    boolean isLong() {
        return durationMinutes > 120;
    }
}
