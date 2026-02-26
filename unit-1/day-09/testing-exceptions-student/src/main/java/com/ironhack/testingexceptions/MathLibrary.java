package com.ironhack.testingexceptions;

public class MathLibrary {

    // Returns a * b, or throws IllegalArgumentException if the result overflows int
    public static int multiply(int a, int b) {
        int product = a * b;
        if (product / a != b) {
            throw new IllegalArgumentException("Product of input is too great for int");
        }
        return product;
    }
}
