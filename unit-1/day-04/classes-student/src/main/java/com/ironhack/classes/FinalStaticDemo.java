package com.ironhack.classes;

/**
 * Demonstrates final constants, static shared state, and static methods.
 */
public class FinalStaticDemo {

    public static void run() {

        // Accessing final constants via the class name
        System.out.println("Max ticket price: $" + TicketCounter.MAX_TICKET_PRICE);
        System.out.println("Max seats per booking: " + TicketCounter.MAX_SEATS_PER_BOOKING);

        // Static counter pattern — shared state across objects
        System.out.println("\n-- Static Counter Pattern --");

        TicketCounter.resetTotalTicketsSold();

        TicketCounter lobby = new TicketCounter("Lobby");
        TicketCounter online = new TicketCounter("Online");

        lobby.sellTickets(3);
        online.sellTickets(2);
        lobby.sellTickets(1);

        System.out.println("\nLobby sold: " + lobby.getTicketsSoldHere());
        System.out.println("Online sold: " + online.getTicketsSoldHere());
        System.out.println("TOTAL sold (all counters): " + TicketCounter.getTotalTicketsSold());

        // Validation using constants
        System.out.println("\n-- Validation Using Constants --");

        online.sellTickets(7);
        online.sellTickets(-1);

        System.out.println("Online sold after rejections: " + online.getTicketsSoldHere());

        // Static utility method
        System.out.println("\n-- Static Utility Method --");

        double revenue = TicketCounter.calculateRevenue(6, 14.50);
        System.out.println("Revenue for 6 tickets at $14.50 each: $" + revenue);

        // Final instance field — set once in constructor, cannot change
        System.out.println("\n-- Final Instance Field --");

        System.out.println("Lobby counter name: " + lobby.getCounterName());
    }
}
