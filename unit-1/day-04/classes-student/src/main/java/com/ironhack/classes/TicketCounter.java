package com.ironhack.classes;

/**
 * Demonstrates final constants, static fields (shared state), and static methods.
 */
public class TicketCounter {

    public static final double MAX_TICKET_PRICE = 50.00;
    public static final int MAX_SEATS_PER_BOOKING = 6;

    // Static field — shared across all instances
    private static int totalTicketsSold = 0;

    // Instance fields — per object
    private final String counterName;
    private int ticketsSoldHere;

    public TicketCounter(String counterName) {
        this.counterName = counterName;
        this.ticketsSoldHere = 0;
    }

    public void sellTickets(int quantity) {
        if (quantity <= 0) {
            System.out.println("  [REJECTED] Quantity must be positive.");
            return;
        }
        if (quantity > MAX_SEATS_PER_BOOKING) {
            System.out.println("  [REJECTED] Cannot sell more than " + MAX_SEATS_PER_BOOKING
                    + " tickets per booking.");
            return;
        }
        this.ticketsSoldHere += quantity;
        totalTicketsSold += quantity;
        System.out.println("  " + counterName + " sold " + quantity
                + " ticket(s). Total here: " + ticketsSoldHere);
    }

    // Static method — called on the class, not an object
    public static int getTotalTicketsSold() {
        return totalTicketsSold;
    }

    // Static utility method — pure calculation, no instance state needed
    public static double calculateRevenue(int ticketCount, double pricePerTicket) {
        return ticketCount * pricePerTicket;
    }

    public String getCounterName() {
        return counterName;
    }

    public int getTicketsSoldHere() {
        return ticketsSoldHere;
    }

    public static void resetTotalTicketsSold() {
        totalTicketsSold = 0;
    }
}
