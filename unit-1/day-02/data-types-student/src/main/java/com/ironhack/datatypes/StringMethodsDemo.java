package com.ironhack.datatypes;

// Demonstrates String methods, .equals() vs ==, and String.format().
public class StringMethodsDemo {
    public static void run() {
        // --- Basics and length ---
        String greeting = "Welcome to Java Beans Cafe!";
        System.out.println(greeting);
        System.out.println("Length: " + greeting.length());

        // --- charAt (zero-based index) ---
        String bookIsbn = "978-3-16-148410-0";

        char firstChar = bookIsbn.charAt(0);
        char lastChar = bookIsbn.charAt(bookIsbn.length() - 1);
        System.out.println("\nISBN: " + bookIsbn);
        System.out.println("First char: " + firstChar);
        System.out.println("Last char: " + lastChar);

        // --- substring (endIndex is exclusive) ---
        String orderCode = "ORD-2024-CAFE-0042";
        String prefix = orderCode.substring(0, 3);
        String year = orderCode.substring(4, 8);
        String orderNum = orderCode.substring(14);
        System.out.println("\nOrder code: " + orderCode);
        System.out.println("Prefix: " + prefix);
        System.out.println("Year: " + year);
        System.out.println("Order number: " + orderNum);

        // --- Searching: indexOf, contains, startsWith, endsWith ---
        String menuDescription = "Our signature blend combines Ethiopian and Colombian beans";

        int blendPos = menuDescription.indexOf("blend");
        int lattePos = menuDescription.indexOf("latte");
        System.out.println("\n\"blend\" found at index: " + blendPos);
        System.out.println("\"latte\" found at index: " + lattePos);

        boolean hasEthiopian = menuDescription.contains("Ethiopian");
        boolean hasBrazilian = menuDescription.contains("Brazilian");
        System.out.println("Contains Ethiopian: " + hasEthiopian);
        System.out.println("Contains Brazilian: " + hasBrazilian);

        boolean isOrderCode = orderCode.startsWith("ORD");
        boolean endsWithNumber = orderCode.endsWith("0042");
        System.out.println("Starts with ORD: " + isOrderCode);
        System.out.println("Ends with 0042: " + endsWithNumber);

        // --- trim, toUpperCase, toLowerCase ---
        String customerInput = "  Carlos Mendez  ";

        String trimmed = customerInput.trim();
        System.out.println("\nBefore trim: [" + customerInput + "]");
        System.out.println("After trim:  [" + trimmed + "]");

        String drinkOrder = "Caramel Macchiato";
        System.out.println("Upper: " + drinkOrder.toUpperCase());
        System.out.println("Lower: " + drinkOrder.toLowerCase());

        // equalsIgnoreCase for case-insensitive comparison
        String userSearch = "ESPRESSO";
        String menuItem = "Espresso";
        boolean matches = userSearch.equalsIgnoreCase(menuItem);
        System.out.println("\"" + userSearch + "\" matches \"" + menuItem + "\": " + matches);

        // --- .equals() vs == ---
        // == compares references (memory addresses), .equals() compares content
        String drink1 = "Espresso";
        String drink2 = "Espresso";
        String drink3 = new String("Espresso");

        System.out.println("\n--- .equals() vs == ---");
        System.out.println("drink1 == drink2: " + (drink1 == drink2));
        System.out.println("drink1 == drink3: " + (drink1 == drink3));
        System.out.println("drink1.equals(drink3): " + drink1.equals(drink3));

        // --- replace and split ---
        String rawPrice = "$4.75 USD";
        String numericOnly = rawPrice.replace("$", "").replace(" USD", "");
        System.out.println("\nRaw: " + rawPrice + " -> Numeric: " + numericOnly);

        String orderItems = "Espresso,Muffin,Latte,Croissant";
        String[] items = orderItems.split(",");
        System.out.println("Order contains " + items.length + " items:");
        for (int i = 0; i < items.length; i++) {
            System.out.println("  " + (i + 1) + ". " + items[i]);
        }

        // split() takes a regex; use "\\." to split on a literal dot
        String version = "2.4.1";
        String[] parts = version.split("\\.");
        System.out.println("Version parts: major=" + parts[0] + ", minor=" + parts[1] + ", patch=" + parts[2]);

        // --- String.format ---
        String customerName = "Sofia";
        String drinkName = "Oat Milk Latte";
        double price = 5.50;
        int loyaltyPoints = 120;

        String formatted = String.format("Customer: %s | Order: %s | Price: $%.2f | Points: %d",
                customerName, drinkName, price, loyaltyPoints);
        System.out.println("\n" + formatted);

        // %.2f always shows 2 decimal places (important for money)
        System.out.println(String.format("Price: $%.2f", 4.5));
        System.out.println(String.format("Price: $%.2f", 4.0));

        // Padding and alignment
        System.out.println(String.format("%-20s $%6.2f", "Espresso", 4.75));
        System.out.println(String.format("%-20s $%6.2f", "Oat Milk Latte", 5.50));
        System.out.println(String.format("%-20s $%6.2f", "Blueberry Muffin", 3.50));

        // --- Concatenation gotcha ---
        System.out.println("\n--- Concatenation gotcha ---");
        System.out.println("Items: " + 3 + 2);           // "Items: 32"
        System.out.println("Items: " + (3 + 2));          // "Items: 5"
        System.out.println(3 + 2 + " items");             // "5 items"
    }
}
