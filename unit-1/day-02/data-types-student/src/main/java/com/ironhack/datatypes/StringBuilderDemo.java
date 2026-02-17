package com.ironhack.datatypes;

// Demonstrates StringBuilder for efficient string building, especially in loops.
public class StringBuilderDemo {
    public static void run() {
        // --- Problem: String + in loops creates many intermediate objects ---
        String orderListBad = "";
        String[] coffeeOrders = {"Espresso", "Latte", "Cappuccino", "Americano", "Mocha"};
        double[] prices = {3.50, 4.75, 4.50, 3.75, 5.25};

        for (int i = 0; i < coffeeOrders.length; i++) {
            orderListBad += (i + 1) + ". " + coffeeOrders[i] + " - $" + prices[i] + "\n";
        }
        System.out.println("Order list (built with +):");
        System.out.println(orderListBad);

        // --- StringBuilder: the efficient way ---
        StringBuilder receipt = new StringBuilder();

        receipt.append("╔════════════════════════════════╗\n");
        receipt.append("║    JAVA BEANS CAFE & BOOKS     ║\n");
        receipt.append("╠════════════════════════════════╣\n");

        double total = 0.0;
        for (int i = 0; i < coffeeOrders.length; i++) {
            receipt.append(String.format("║ %d. %-15s  $%5.2f   ║\n",
                    i + 1, coffeeOrders[i], prices[i]));
            total += prices[i];
        }

        receipt.append("╠════════════════════════════════╣\n");
        receipt.append(String.format("║ SUBTOTAL:          $%5.2f   ║\n", total));

        double tax = total * 0.08;
        receipt.append(String.format("║ TAX (8%%):           $%5.2f   ║\n", tax));

        double grandTotal = total + tax;
        receipt.append(String.format("║ TOTAL:             $%5.2f   ║\n", grandTotal));
        receipt.append("╚════════════════════════════════╝");

        System.out.println(receipt.toString());

        // --- StringBuilder methods ---
        StringBuilder order = new StringBuilder("Order: ");
        order.append("Espresso");
        order.append(", Muffin");
        order.append(", Cookie");
        System.out.println("\n" + order);

        // insert at a specific position
        order.insert(7, "RUSH - ");
        System.out.println("After insert: " + order);

        // delete a section
        int cookieStart = order.indexOf(", Cookie");
        if (cookieStart != -1) {
            order.delete(cookieStart, cookieStart + ", Cookie".length());
        }
        System.out.println("After delete: " + order);

        // replace a section
        int espressoStart = order.indexOf("Espresso");
        if (espressoStart != -1) {
            order.replace(espressoStart, espressoStart + "Espresso".length(), "Double Espresso");
        }
        System.out.println("After replace: " + order);

        // reverse
        StringBuilder palindromeCheck = new StringBuilder("racecar");
        String original = palindromeCheck.toString();
        String reversed = palindromeCheck.reverse().toString();
        System.out.println("\n\"" + original + "\" reversed is \"" + reversed + "\"");
        System.out.println("Is palindrome: " + original.equals(reversed));

        // length vs capacity
        StringBuilder demo = new StringBuilder();
        System.out.println("\nEmpty StringBuilder - length: " + demo.length() + ", capacity: " + demo.capacity());
        demo.append("Hello");
        System.out.println("After 'Hello'   - length: " + demo.length() + ", capacity: " + demo.capacity());

        // --- Method chaining ---
        String bookReceipt = new StringBuilder()
                .append("Book Order Receipt\n")
                .append("------------------\n")
                .append("1. The Great Gatsby    $12.99\n")
                .append("2. Dune                $14.50\n")
                .append("3. Neuromancer          $9.99\n")
                .append("------------------\n")
                .append("Total: $37.48\n")
                .toString();

        System.out.println("\n" + bookReceipt);

        // --- Building dynamic content with conditionals in a loop ---
        String[] customerNames = {"Carlos", "Sofia", "Akira", "Priya"};
        int[] customerPoints = {3200, 5100, 800, 1500};

        StringBuilder summary = new StringBuilder();
        summary.append("=== Loyalty Program Summary ===\n");

        for (int i = 0; i < customerNames.length; i++) {
            String custTier;
            if (customerPoints[i] >= 5000) {
                custTier = "PLATINUM";
            } else if (customerPoints[i] >= 2500) {
                custTier = "GOLD";
            } else if (customerPoints[i] >= 1000) {
                custTier = "SILVER";
            } else {
                custTier = "BRONZE";
            }

            summary.append(String.format("%-8s | %5d pts | %s\n",
                    customerNames[i], customerPoints[i], custTier));
        }

        summary.append("===============================");
        System.out.println(summary);
    }
}
