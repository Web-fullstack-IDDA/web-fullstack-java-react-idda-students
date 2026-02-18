package com.ironhack.loops;

// Break exits the loop entirely. Continue skips the current iteration and moves to the next.
public class BreakContinueDemo {
    public static void run() {

        // Break -- exit the loop early when a match is found
        String[] reservations = {"Harper", "Dimitri", "Yuki", "Soren", "Amira"};
        String searchName = "Yuki";

        System.out.println("--- Searching for reservation: " + searchName + " ---");
        boolean found = false;

        for (int i = 0; i < reservations.length; i++) {
            System.out.println("Checking: " + reservations[i]);
            if (reservations[i].equals(searchName)) {
                System.out.println("Found " + searchName + " at position " + i + "!");
                found = true;
                break;
            }
        }
        if (!found) {
            System.out.println(searchName + " not found in reservations");
        }

        // Break in a while loop (stop at sentinel value)
        int[] orderNumbers = {101, 102, 103, 0, 104, 105};

        System.out.println("\n--- Processing orders (stop at sentinel 0) ---");
        int idx = 0;
        while (idx < orderNumbers.length) {
            if (orderNumbers[idx] == 0) {
                System.out.println("Sentinel value reached -- stopping");
                break;
            }
            System.out.println("Processing order #" + orderNumbers[idx]);
            idx++;
        }

        // Continue -- skip unavailable dishes
        double[] dishPrices = {12.50, 0.00, 8.75, 0.00, 15.00, 6.25, 0.00};

        System.out.println("\n--- Available dishes and prices ---");
        for (int i = 0; i < dishPrices.length; i++) {
            if (dishPrices[i] == 0.00) {
                continue;
            }
            System.out.println("Dish #" + (i + 1) + ": $" + dishPrices[i]);
        }

        // Continue in a for-each loop
        String[] feedback = {"great", "skip", "amazing", "skip", "delicious", "wonderful"};

        System.out.println("\n--- Displaying valid customer feedback ---");
        for (String comment : feedback) {
            if (comment.equals("skip")) {
                continue;
            }
            System.out.println("Customer says: \"" + comment + "\"");
        }

        // Break vs continue side by side
        int[] ratings = {5, 4, 3, 1, 5, 2, 4};

        // Break: stop at the first bad rating
        System.out.println("\n--- Ratings until first bad one (BREAK) ---");
        for (int rating : ratings) {
            if (rating < 3) {
                System.out.println("Bad rating found (" + rating + ") -- stopping review");
                break;
            }
            System.out.println("Rating: " + rating + " stars");
        }

        // Continue: show only good ratings, skip the bad ones
        System.out.println("\n--- Only good ratings (CONTINUE) ---");
        for (int rating : ratings) {
            if (rating < 3) {
                continue;
            }
            System.out.println("Rating: " + rating + " stars");
        }

        // Practical example: allergen check with nested loops
        String[] allergens = {"Gluten", "Dairy", "Nuts", "Soy", "Shellfish"};
        String[] customerAllergies = {"Nuts", "Eggs"};

        System.out.println("\n--- Allergen check ---");
        boolean allergenWarning = false;

        for (String menuAllergen : allergens) {
            for (String allergy : customerAllergies) {
                if (menuAllergen.equals(allergy)) {
                    System.out.println("WARNING: Menu contains " + allergy + "!");
                    allergenWarning = true;
                    break; // Only breaks the inner loop
                }
            }
        }
        if (!allergenWarning) {
            System.out.println("No allergen conflicts found");
        }
    }
}
