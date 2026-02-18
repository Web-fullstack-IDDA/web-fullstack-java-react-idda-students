package com.ironhack.loops;

// For loop: iterate a known number of times using initialization, condition, and increment.
public class ForLoopDemo {
    public static void run() {

        // Basic for loop -- counting
        System.out.println("--- Printing table numbers (1 through 5) ---");
        for (int i = 1; i <= 5; i++) {
            System.out.println("Table " + i + " is ready");
        }

        // Iterating over an array with a for loop
        String[] appetizers = {"Bruschetta", "Soup of the Day", "Spring Rolls", "Garlic Bread", "Edamame"};

        System.out.println("\n--- Appetizer Menu ---");
        for (int i = 0; i < appetizers.length; i++) {
            System.out.println((i + 1) + ". " + appetizers[i]);
        }

        // Using the index to work with multiple arrays
        String[] beverages = {"Espresso", "Lemonade", "Sparkling Water", "Iced Tea"};
        double[] beveragePrices = {3.50, 4.25, 2.00, 3.75};

        System.out.println("\n--- Beverage Menu with Prices ---");
        for (int i = 0; i < beverages.length; i++) {
            System.out.println(beverages[i] + " - $" + beveragePrices[i]);
        }

        // Looping backwards
        System.out.println("\n--- Last orders (reverse priority) ---");
        String[] lastOrders = {"Cheesecake", "Coffee", "Mints"};
        for (int i = lastOrders.length - 1; i >= 0; i--) {
            System.out.println(lastOrders[i]);
        }

        // Custom increment (skip every other element)
        int[] caloriesPerDish = {650, 320, 480, 550, 410, 780};

        System.out.println("\n--- Every other dish's calories ---");
        for (int i = 0; i < caloriesPerDish.length; i += 2) {
            System.out.println("Dish at index " + i + ": " + caloriesPerDish[i] + " cal");
        }

        // Calculating a total (accumulator pattern)
        double[] orderPrices = {12.99, 9.50, 18.75, 8.50};
        double totalBill = 0;

        System.out.println("\n--- Calculating the bill ---");
        for (int i = 0; i < orderPrices.length; i++) {
            totalBill += orderPrices[i];
        }
        System.out.println("Total bill: $" + totalBill);
    }
}
