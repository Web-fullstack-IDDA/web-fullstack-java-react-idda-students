package com.ironhack.bigdecimal;

/**
 * Demonstrates why double fails for decimal math.
 */
public class FloatingPointProblem {

    public static void run() {

        // Example 1: 0.7 + 0.1 should equal 0.8
        double a = 0.7;
        double b = 0.1;
        double sum = a + b;

        System.out.println("--- Example 1: Addition ---");
        System.out.println("0.7 + 0.1 = " + sum);
        System.out.println("sum == 0.8? " + (sum == 0.8));
        System.out.println();

        // Example 2: 0.2 + 0.1 should equal 0.3
        double c = 0.2;
        double sum2 = c + b;

        System.out.println("--- Example 2: Another Addition ---");
        System.out.println("0.2 + 0.1 = " + sum2);
        System.out.println("sum == 0.3? " + (sum2 == 0.3));
        System.out.println();

        // Example 3: Accumulation error in a loop
        double total = 0.0;
        for (int i = 0; i < 10; i++) {
            total += 0.1;
        }

        System.out.println("--- Example 3: Accumulation ---");
        System.out.println("0.1 added 10 times = " + total);
        System.out.println("total == 1.0? " + (total == 1.0));
    }
}
