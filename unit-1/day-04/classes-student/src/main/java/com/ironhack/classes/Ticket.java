package com.ironhack.classes;

/**
 * Demonstrates constructors: no-arg, parameterized, overloading, and the `this` keyword.
 */
public class Ticket {

    String movieTitle;
    String seatNumber;
    double price;
    boolean isVIP;

    // No-arg constructor — sets default values
    Ticket() {
        this.movieTitle = "Unassigned";
        this.seatNumber = "N/A";
        this.price = 0.0;
        this.isVIP = false;
    }

    // Parameterized constructor — uses `this` to distinguish fields from parameters
    Ticket(String movieTitle, String seatNumber, double price) {
        this.movieTitle = movieTitle;
        this.seatNumber = seatNumber;
        this.price = price;
        this.isVIP = false;
    }

    // Overloaded constructor — adds VIP parameter
    Ticket(String movieTitle, String seatNumber, double price, boolean isVIP) {
        this.movieTitle = movieTitle;
        this.seatNumber = seatNumber;
        this.price = price;
        this.isVIP = isVIP;
    }

    String getDetails() {
        String vipLabel = isVIP ? " [VIP]" : "";
        return movieTitle + " | Seat: " + seatNumber + " | $" + price + vipLabel;
    }
}
