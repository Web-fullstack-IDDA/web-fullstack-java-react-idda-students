package com.ironhack.loops;

// Arrays: fixed-size containers holding multiple values of the same type.
public class ArraysDemo {
    public static void run() {

        // Declaring an array with values
        String[] dishes = {"Margherita Pizza", "Caesar Salad", "Grilled Salmon", "Tiramisu"};

        // Printing an array directly gives a memory reference, not the contents
        System.out.println("Today's specials: " + dishes);

        // Accessing elements (zero-indexed)
        System.out.println("First dish (index 0): " + dishes[0]);
        System.out.println("Second dish (index 1): " + dishes[1]);
        System.out.println("Last dish (index 3): " + dishes[3]);

        // The .length property (no parentheses)
        System.out.println("Number of dishes on menu: " + dishes.length);
        System.out.println("Last dish using length: " + dishes[dishes.length - 1]);

        // Declaring an array with a specific size (values default to 0.0)
        double[] prices = new double[4];
        System.out.println("Price before assignment: " + prices[0]);

        // Modifying elements
        prices[0] = 12.99;
        prices[1] = 9.50;
        prices[2] = 18.75;
        prices[3] = 7.25;

        System.out.println("Margherita Pizza costs: $" + prices[0]);
        System.out.println("Grilled Salmon costs: $" + prices[2]);

        // Updating an existing value
        System.out.println("Original Tiramisu price: $" + prices[3]);
        prices[3] = 8.50;
        System.out.println("Updated Tiramisu price: $" + prices[3]);

        // Arrays of different types
        int[] orderQuantities = {2, 1, 3, 1};
        boolean[] isVegetarian = {true, true, false, true};

        System.out.println("Salmon is vegetarian? " + isVegetarian[2]);
        System.out.println("Pizzas ordered: " + orderQuantities[0]);

        // Arrays have a fixed length -- you cannot add or remove elements
        System.out.println("Array length is fixed at: " + dishes.length);
    }
}
