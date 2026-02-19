package com.ironhack.classes;

/**
 * Demonstrates using different constructors and the effect of parameter shadowing with `this`.
 */
public class ConstructorDemo {

    public static void run() {

        // No-arg constructor — default values
        Ticket blankTicket = new Ticket();
        System.out.println("Blank ticket: " + blankTicket.getDetails());

        // Parameterized constructor — specific values
        Ticket regularTicket = new Ticket("Dune: Part Two", "B12", 14.50);
        System.out.println("Regular ticket: " + regularTicket.getDetails());

        // Overloaded constructor — with VIP
        Ticket vipTicket = new Ticket("Dune: Part Two", "A1", 35.00, true);
        System.out.println("VIP ticket: " + vipTicket.getDetails());

        // Parameter shadowing demo
        System.out.println("\n-- Parameter Shadowing Demo --");
        demonstrateThisKeyword();
    }

    private static void demonstrateThisKeyword() {

        // Without `this` — parameter shadows the field, field stays null
        class BrokenMovie {
            String title;

            BrokenMovie(String title) {
                title = title;  // Bug: assigns parameter to itself
            }
        }

        // With `this` — field is correctly set
        class CorrectMovie {
            String title;

            CorrectMovie(String title) {
                this.title = title;
            }
        }

        BrokenMovie broken = new BrokenMovie("Interstellar");
        CorrectMovie correct = new CorrectMovie("Interstellar");

        System.out.println("Without this: title = " + broken.title);
        System.out.println("With this:    title = " + correct.title);
    }
}
