package com.ironhack.filehandling;

import java.util.Scanner;

/**
 * Demonstrates Scanner with System.in: nextLine(), nextInt(), nextDouble(), next().
 * Uses try-with-resources so .close() is called automatically.
 */
public class ScannerDemo {

    public static void run() {

        // try-with-resources: no need to call scanner.close() manually
        try (Scanner scanner = new Scanner(System.in)) {

            // nextLine() — reads a full line
            System.out.println("Enter your name:");
            String name = scanner.nextLine();
            System.out.println("Hello, " + name + "!");

            // nextInt() — reads an integer
            System.out.println("Enter your age:");
            int age = scanner.nextInt();
            scanner.nextLine(); // flush leftover newline

            // nextDouble() — reads a decimal number
            System.out.println("Enter your book budget:");
            double budget = scanner.nextDouble();
            scanner.nextLine(); // flush leftover newline

            // nextLine() — reads full line again
            System.out.println("Enter your favorite book title:");
            String book = scanner.nextLine();

            // next() — reads one word (stops at space)
            System.out.println("Enter a genre (one word):");
            String genre = scanner.next();

            // Print summary
            System.out.println("\n--- Library Card Summary ---");
            System.out.println("Name: " + name);
            System.out.println("Age: " + age);
            System.out.println("Budget: $" + budget);
            System.out.println("Favorite book: " + book);
            System.out.println("Genre: " + genre);

            if (age >= 18) {
                System.out.println("Welcome to the library!");
            } else {
                System.out.println("Welcome! A parent must sign your card.");
            }
        }
    }
}
