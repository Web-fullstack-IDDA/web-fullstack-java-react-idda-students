package com.ironhack.filehandling;

import java.io.FileWriter;
import java.io.IOException;

/**
 * Demonstrates FileWriter: overwrite mode, append mode, and try-with-resources.
 */
public class FileWriterDemo {

    public static void run() throws IOException {

        // Overwrite mode — creates or replaces the file
        try (FileWriter writer = new FileWriter("book_catalog.txt")) {
            writer.write("=== Ironhack Library Catalog ===\n");
            writer.write("1. Dune — Frank Herbert\n");
            writer.write("2. Neuromancer — William Gibson\n");
            writer.write("3. Fahrenheit 451 — Ray Bradbury\n");

            System.out.println("Catalog written to book_catalog.txt (overwrite mode)");
        }

        // Overwrite again — previous content is replaced
        try (FileWriter writer = new FileWriter("book_catalog.txt")) {
            writer.write("=== Ironhack Library Catalog (Updated) ===\n");
            writer.write("1. The Left Hand of Darkness — Ursula K. Le Guin\n");
            writer.write("2. Kindred — Octavia Butler\n");

            System.out.println("Catalog OVERWRITTEN with new data");
        }

        // Append mode — adds to existing content
        try (FileWriter writer = new FileWriter("book_catalog.txt", true)) {
            writer.write("3. Parable of the Sower — Octavia Butler\n");
            writer.write("4. Snow Crash — Neal Stephenson\n");

            System.out.println("Two more books APPENDED to book_catalog.txt");
        }

        // Writing multiple lines in a loop
        String[] sciFiBooks = {
            "Foundation — Isaac Asimov",
            "Ender's Game — Orson Scott Card",
            "The Hitchhiker's Guide to the Galaxy — Douglas Adams"
        };

        try (FileWriter writer = new FileWriter("scifi_shelf.txt")) {
            for (int i = 0; i < sciFiBooks.length; i++) {
                writer.write((i + 1) + ". " + sciFiBooks[i] + "\n");
            }

            System.out.println("Sci-Fi shelf written to scifi_shelf.txt (" + sciFiBooks.length + " books)");
        }
    }
}
