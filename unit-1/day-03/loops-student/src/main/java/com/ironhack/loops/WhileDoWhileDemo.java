package com.ironhack.loops;

// While loop: condition checked before each iteration. Do-while: body runs at least once.
public class WhileDoWhileDemo {
    public static void run() {

        // Basic while loop
        System.out.println("--- Serving customers in the queue ---");
        int customersInQueue = 5;

        while (customersInQueue > 0) {
            System.out.println("Serving customer... " + customersInQueue + " left in queue");
            customersInQueue--;
        }
        System.out.println("All customers served!");

        // While loop with an array
        String[] ingredients = {"Flour", "Sugar", "Butter", "Eggs", "Vanilla"};
        int index = 0;

        System.out.println("\n--- Recipe ingredients ---");
        while (index < ingredients.length) {
            System.out.println("- " + ingredients[index]);
            index++;
        }

        // While loop with a false condition from start (body never runs)
        int seatsAvailable = 0;

        System.out.println("\n--- Checking for available seats ---");
        while (seatsAvailable > 0) {
            System.out.println("This will never print");
            seatsAvailable--;
        }
        System.out.println("No seats available -- while loop body was skipped entirely");

        // While loop -- ordering within a budget
        double[] menuPrices = {6.50, 4.75, 11.00, 8.25, 3.50, 9.00, 5.25};
        double budget = 25.00;
        double spent = 0;
        int itemsBought = 0;

        System.out.println("\n--- Ordering within a $25 budget ---");
        while (itemsBought < menuPrices.length && spent + menuPrices[itemsBought] <= budget) {
            spent += menuPrices[itemsBought];
            System.out.println("Ordered item #" + (itemsBought + 1) + " ($" + menuPrices[itemsBought] + ") -- Total so far: $" + spent);
            itemsBought++;
        }
        System.out.println("Bought " + itemsBought + " items, spent $" + spent + " of $" + budget + " budget");

        // Do-while loop -- executes at least once
        System.out.println("\n--- Preparing plates (do-while) ---");
        int platesToPrepare = 3;
        int platesReady = 0;

        do {
            platesReady++;
            System.out.println("Plate #" + platesReady + " is ready!");
        } while (platesReady < platesToPrepare);

        System.out.println("All " + platesReady + " plates are ready to serve");

        // Do-while runs once even when condition is false
        int ordersRemaining = 0;

        System.out.println("\n--- Do-while with false condition ---");
        do {
            System.out.println("Kitchen check: processing any remaining order...");
        } while (ordersRemaining > 0);
        System.out.println("Done! The body ran once despite the condition being false");

        // Infinite loop warning (commented out for safety)
        // while (true) {
        //     System.out.println("This runs forever!");
        // }
        System.out.println("\n(Infinite loop example is commented out for safety)");
    }
}
