package com.ironhack.loops;

// For-each loop: simpler syntax when you need each value but not the index.
public class ForEachDemo {
    public static void run() {

        // Basic for-each loop
        String[] desserts = {"Panna Cotta", "Chocolate Lava Cake", "Fruit Tart", "Gelato"};

        System.out.println("--- Dessert Menu ---");
        for (String dessert : desserts) {
            System.out.println("Try our " + dessert + "!");
        }

        // For-each with numeric arrays
        int[] waitTimesMinutes = {15, 22, 8, 30, 12};

        System.out.println("\n--- Current wait times ---");
        for (int minutes : waitTimesMinutes) {
            System.out.println("Estimated wait: " + minutes + " minutes");
        }

        // Accumulator pattern with for-each
        double[] tips = {5.00, 3.50, 8.25, 2.75, 6.00};
        double totalTips = 0;

        for (double tip : tips) {
            totalTips += tip;
        }
        System.out.println("\nTotal tips collected: $" + totalTips);

        // Comparison: for vs for-each
        String[] chefs = {"Marco", "Elena", "Kenji", "Amara"};

        System.out.println("\n--- Chef roster (for loop - with index) ---");
        for (int i = 0; i < chefs.length; i++) {
            System.out.println("Chef #" + (i + 1) + ": " + chefs[i]);
        }

        System.out.println("\n--- Chef roster (for-each - no index) ---");
        for (String chef : chefs) {
            System.out.println("Chef: " + chef);
        }

        // Limitation: for-each cannot modify array elements
        int[] servingSizes = {150, 200, 175, 250};

        System.out.println("\n--- Attempting to double serving sizes ---");

        // This does NOT modify the original array (the variable is a copy)
        for (int size : servingSizes) {
            size = size * 2;
        }
        System.out.println("First serving size (still original): " + servingSizes[0]);

        // Use a regular for loop to actually modify the array
        for (int i = 0; i < servingSizes.length; i++) {
            servingSizes[i] = servingSizes[i] * 2;
        }
        System.out.println("First serving size (now doubled): " + servingSizes[0]);

        // For-each with booleans
        boolean[] tableOccupied = {true, false, true, true, false, false};
        int availableCount = 0;

        for (boolean occupied : tableOccupied) {
            if (!occupied) {
                availableCount++;
            }
        }
        System.out.println("\nAvailable tables: " + availableCount + " out of " + tableOccupied.length);
    }
}
