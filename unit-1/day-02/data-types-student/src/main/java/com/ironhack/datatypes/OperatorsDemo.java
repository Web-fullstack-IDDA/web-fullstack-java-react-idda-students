package com.ironhack.datatypes;

// Demonstrates arithmetic operators, integer division, modulus, and compound assignment.
public class OperatorsDemo {
    public static void run() {
        // --- Basic arithmetic ---
        double espressoPrice = 4.75;
        int espressoQty = 3;
        double muffinPrice = 3.50;
        int muffinQty = 2;

        double espressoTotal = espressoPrice * espressoQty;
        double muffinTotal = muffinPrice * muffinQty;
        double subtotal = espressoTotal + muffinTotal;
        System.out.println("Espresso subtotal: $" + espressoTotal);
        System.out.println("Muffin subtotal: $" + muffinTotal);
        System.out.println("Order subtotal: $" + subtotal);

        final double TAX_RATE = 0.08;
        double tax = subtotal * TAX_RATE;
        double total = subtotal + tax;
        System.out.println("Tax (8%): $" + tax);
        System.out.println("Order total: $" + total);

        double discount = subtotal * 0.10;
        double discountedTotal = total - discount;
        System.out.println("Member discount (10%): -$" + discount);
        System.out.println("Discounted total: $" + discountedTotal);

        // --- Integer division (truncates the decimal part) ---
        int totalBooks = 17;
        int shelves = 5;

        int booksPerShelf = totalBooks / shelves;       // 3, not 3.4
        int remainingBooks = totalBooks % shelves;      // 2
        System.out.println("\n17 books / 5 shelves = " + booksPerShelf + " per shelf");
        System.out.println("Remaining books: " + remainingBooks);

        // Cast one operand to double for a decimal result
        double exactBooksPerShelf = (double) totalBooks / shelves;
        System.out.println("Exact books per shelf: " + exactBooksPerShelf);

        // Splitting a bill in cents to avoid floating-point issues
        int billCents = 2375;
        int friends = 4;
        int eachPaysCents = billCents / friends;
        int leftoverCents = billCents % friends;
        System.out.println("\nBill: $" + (billCents / 100.0));
        System.out.println("Each of " + friends + " friends pays: $" + (eachPaysCents / 100.0));
        System.out.println("Leftover: " + leftoverCents + " cents");

        // --- Modulus uses ---
        int orderNumber = 147;
        boolean isEvenOrder = (orderNumber % 2 == 0);
        System.out.println("\nOrder #" + orderNumber + " is even: " + isEvenOrder);

        // Round-robin assignment to baristas
        int baristaCount = 3;
        for (int order = 1; order <= 6; order++) {
            int assignedBarista = order % baristaCount;
            System.out.println("Order " + order + " -> Barista " + assignedBarista);
        }

        // --- Compound assignment operators ---
        double runningTotal = 0.0;
        runningTotal += espressoPrice;
        System.out.println("\nAfter espresso: $" + runningTotal);
        runningTotal += muffinPrice;
        System.out.println("After muffin: $" + runningTotal);
        runningTotal *= 1.08;
        System.out.println("After tax: $" + runningTotal);
        runningTotal -= 1.00;
        System.out.println("After $1 credit: $" + runningTotal);

        // --- Increment and decrement ---
        int cupsServed = 0;
        cupsServed++;
        cupsServed++;
        cupsServed++;
        System.out.println("\nCups served: " + cupsServed);

        // Post-increment: use value first, then add 1
        int a = 5;
        int b = a++;
        System.out.println("Post-increment: a=" + a + ", b=" + b);

        // Pre-increment: add 1 first, then use value
        int c = 5;
        int d = ++c;
        System.out.println("Pre-increment:  c=" + c + ", d=" + d);

        // --- Order of operations ---
        double bookOriginal = 24.99;
        double bookDiscount = 0.20;

        double wrongPrice = bookOriginal - bookDiscount * bookOriginal;
        double clearPrice = bookOriginal * (1 - bookDiscount);
        System.out.println("\nBook original: $" + bookOriginal);
        System.out.println("After 20% off (approach 1): $" + wrongPrice);
        System.out.println("After 20% off (approach 2): $" + clearPrice);
    }
}
