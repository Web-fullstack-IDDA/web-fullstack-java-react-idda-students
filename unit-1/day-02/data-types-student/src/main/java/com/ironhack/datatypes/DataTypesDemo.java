package com.ironhack.datatypes;

// Demonstrates Java primitive types, String, type casting, and constants.
public class DataTypesDemo {
    public static void run() {
        // --- Integer types ---
        int quantity = 3;
        int booksInStock = 247;
        System.out.println("Espressos ordered: " + quantity);
        System.out.println("Books in stock: " + booksInStock);

        long totalBeansSoldAllTime = 9_876_543_210L;
        System.out.println("Total beans sold all-time: " + totalBeansSoldAllTime);

        short tableNumber = 14;
        byte floorLevel = 2;
        System.out.println("Table: " + tableNumber + ", Floor: " + floorLevel);

        // --- Floating-point types ---
        double unitPrice = 4.75;
        double bookPrice = 12.99;
        System.out.println("Espresso price: $" + unitPrice);
        System.out.println("Book price: $" + bookPrice);

        float tipPercentage = 0.15f;
        System.out.println("Tip percentage: " + tipPercentage);

        // Floating-point precision: 0.1 + 0.2 is not exactly 0.3
        System.out.println("0.1 + 0.2 = " + (0.1 + 0.2));

        // --- Boolean ---
        boolean isMember = true;
        boolean isDecaf = false;
        boolean isBookOnSale = true;
        System.out.println("Is member: " + isMember);
        System.out.println("Is decaf: " + isDecaf);
        System.out.println("Book on sale: " + isBookOnSale);

        // --- Char (single character, single quotes) ---
        char drinkSize = 'L';
        char memberTier = 'G';
        System.out.println("Drink size code: " + drinkSize);
        System.out.println("Member tier code: " + memberTier);

        // --- String (reference type, double quotes) ---
        String customerName = "Carlos";
        String coffeeOrder = "Double Espresso";
        String bookTitle = "The Great Gatsby";
        System.out.println("Customer: " + customerName);
        System.out.println("Order: " + coffeeOrder);
        System.out.println("Book: " + bookTitle);

        String receipt = customerName + " ordered " + quantity + "x " + coffeeOrder + " at $" + unitPrice + " each";
        System.out.println(receipt);

        // --- Type casting ---
        // Widening (implicit): int -> double
        int itemCount = 5;
        double itemCountAsDouble = itemCount;
        System.out.println("Item count as double: " + itemCountAsDouble);

        // Narrowing (explicit): double -> int (truncates, does NOT round)
        double totalCost = 14.25;
        int truncatedCost = (int) totalCost;
        System.out.println("Total cost: " + totalCost + " -> truncated: " + truncatedCost);

        double almostFive = 4.99;
        int truncated = (int) almostFive;
        System.out.println("4.99 truncated to int: " + truncated);

        // --- Constants (final keyword, UPPER_SNAKE_CASE) ---
        final double TAX_RATE = 0.08;
        final int MAX_ITEMS_PER_ORDER = 20;
        final String STORE_NAME = "Java Beans Cafe & Books";
        System.out.println("Store: " + STORE_NAME);
        System.out.println("Tax rate: " + (TAX_RATE * 100) + "%");
        System.out.println("Max items per order: " + MAX_ITEMS_PER_ORDER);
    }
}
