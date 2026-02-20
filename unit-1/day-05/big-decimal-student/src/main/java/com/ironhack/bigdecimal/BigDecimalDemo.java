package com.ironhack.bigdecimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * BigDecimal — The safe way to do decimal math.
 */
public class BigDecimalDemo {

    public static void run() {

        // Part A: Creating BigDecimal
        System.out.println("--- Part A: Creating BigDecimal ---");

        BigDecimal a = new BigDecimal("0.7");
        BigDecimal b = new BigDecimal("0.1");
        System.out.println("a = " + a);
        System.out.println("b = " + b);

        BigDecimal bad = new BigDecimal(0.7);
        System.out.println("new BigDecimal(0.7) = " + bad);
        System.out.println();

        // Part B: Arithmetic
        System.out.println("--- Part B: Arithmetic ---");

        BigDecimal sum = a.add(b);
        System.out.println("0.7 + 0.1 = " + sum);

        BigDecimal c = new BigDecimal("0.2");
        BigDecimal sum2 = c.add(b);
        System.out.println("0.2 + 0.1 = " + sum2);
        System.out.println();

        // Part C: setScale and Rounding
        System.out.println("--- Part C: setScale and Rounding ---");

        BigDecimal displayVal = sum.setScale(2, RoundingMode.HALF_UP);
        System.out.println("0.7 + 0.1 rounded to 2 places: " + displayVal);

        BigDecimal total = new BigDecimal("10.00");
        BigDecimal perPerson = total.divide(new BigDecimal("3"), 2, RoundingMode.HALF_UP);
        System.out.println("$10.00 / 3 = $" + perPerson + " each");
        System.out.println();

        // Part D: Immutability
        System.out.println("--- Part D: Immutability ---");

        BigDecimal price = new BigDecimal("5.00");

        price.add(new BigDecimal("1.00"));
        System.out.println("After price.add(1.00) without assign: " + price);

        BigDecimal newPrice = price.add(new BigDecimal("1.00"));
        System.out.println("Correctly assigned: " + newPrice);
    }
}
