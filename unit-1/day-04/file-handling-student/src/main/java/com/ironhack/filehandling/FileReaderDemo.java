package com.ironhack.filehandling;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Demonstrates reading files with Scanner(File), hasNextLine() loop, and error handling.
 */
public class FileReaderDemo {

    public static void run() throws FileNotFoundException {

        // Reading a file line by line
        File catalogFile = new File("book_catalog.txt");

        if (!catalogFile.exists()) {
            System.err.println("ERROR: book_catalog.txt not found! Run FileWriterDemo first.");
            return;
        }

        System.out.println("Reading book_catalog.txt:");

        try (Scanner fileScanner = new Scanner(catalogFile)) {
            int lineNumber = 0;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                lineNumber++;
                System.out.println("  Line " + lineNumber + ": " + line);
            }

            System.out.println("Total lines read: " + lineNumber);
        }

        // Reading and searching for specific data
        File sciFiFile = new File("scifi_shelf.txt");

        if (!sciFiFile.exists()) {
            System.err.println("ERROR: scifi_shelf.txt not found!");
            return;
        }

        System.out.println("\nSearching scifi_shelf.txt for books by 'Adams':");

        try (Scanner fileScanner = new Scanner(sciFiFile)) {
            boolean found = false;

            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();

                if (line.contains("Adams")) {
                    System.out.println("  FOUND: " + line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("  No books by 'Adams' found.");
            }
        }

        // Handling a missing file gracefully
        File missingFile = new File("nonexistent_catalog.txt");

        try (Scanner missingScanner = new Scanner(missingFile)) {
            System.out.println(missingScanner.nextLine());
        } catch (FileNotFoundException e) {
            System.err.println("File not found: " + missingFile.getName() + " — " + e.getMessage());
        }
    }
}
