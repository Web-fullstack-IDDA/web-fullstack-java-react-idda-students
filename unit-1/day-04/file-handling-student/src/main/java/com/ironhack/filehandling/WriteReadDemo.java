package com.ironhack.filehandling;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * Full round-trip: write book checkout records to a file, then read them back.
 */
public class WriteReadDemo {

    public static void run() throws IOException {

        String filename = "checkout_log.txt";

        // Write initial checkout records (overwrite mode)
        try (FileWriter writer = new FileWriter(filename)) {
            writer.write("=== Library Checkout Log ===\n");
            writer.write("2025-09-01 | Dune | Maria Santos\n");
            writer.write("2025-09-01 | Neuromancer | Alex Rivera\n");
            writer.write("2025-09-02 | Kindred | Priya Patel\n");

            System.out.println("Initial checkout log written to " + filename);
        }

        // Append more records (append mode)
        try (FileWriter writer = new FileWriter(filename, true)) {
            writer.write("2025-09-03 | Snow Crash | James Chen\n");
            writer.write("2025-09-03 | Foundation | Maria Santos\n");

            System.out.println("Two more checkouts appended to " + filename);
        }

        // Read the entire file back
        File logFile = new File(filename);

        System.out.println("\nFull checkout log:");

        try (Scanner reader = new Scanner(logFile)) {
            while (reader.hasNextLine()) {
                System.out.println("  " + reader.nextLine());
            }
        }

        // Count checkouts for a specific person
        String searchName = "Maria Santos";
        int checkoutCount = 0;

        try (Scanner reader = new Scanner(logFile)) {
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                if (line.contains(searchName)) {
                    checkoutCount++;
                }
            }
        }

        System.out.println("\nCheckouts by " + searchName + ": " + checkoutCount);

        // Clean up demo files
        new File("book_catalog.txt").delete();
        new File("scifi_shelf.txt").delete();
        new File(filename).delete();

        System.out.println("Demo files cleaned up.");
    }
}
