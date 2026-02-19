package com.ironhack.filehandling;

/**
 * Demonstrates System.out (standard output) vs System.err (standard error).
 */
public class OutputStreamsDemo {

    public static void run() {

        // Normal output with System.out
        System.out.println("Library Inventory Check");
        System.out.println("Book: 'Dune' — Copies in stock: 7");

        System.out.print("Status: ");
        System.out.println("Available");

        // Error output with System.err
        System.err.println("WARNING: Book 'Infinite Jest' has 0 copies — reorder needed!");

        // Validate a book price
        double bookPrice = -5.99;

        if (bookPrice < 0) {
            System.err.println("ERROR: Invalid price for book: " + bookPrice);
        } else {
            System.out.println("Book price accepted: $" + bookPrice);
        }

        double validPrice = 14.99;
        if (validPrice < 0) {
            System.err.println("ERROR: Invalid price for book: " + validPrice);
        } else {
            System.out.println("Book price accepted: $" + validPrice);
        }

        // printf — formatted output
        String bookTitle = "Brave New World";
        int copies = 12;
        double price = 9.95;

        System.out.printf("Book: '%s' | Copies: %d | Price: $%.2f%n", bookTitle, copies, price);
    }
}
