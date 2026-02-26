package com.ironhack.exceptions;

import java.io.FileNotFoundException;
import java.io.FileReader;

// Core exception-handling concepts
public class Main {

    public static void main(String[] args) {

        // ---- 1. Catching a Checked Exception ----
        System.out.println("=== 1. Catching a Checked Exception ===");
        readFile("missing-file.txt");
        System.out.println();

        // ---- 2. Catching an Unchecked Exception ----
        System.out.println("=== 2. Catching an Unchecked Exception ===");
        printLength("Hello");
        printLength(null);
        System.out.println();

        // ---- 3. Throwing Your Own Exceptions ----
        System.out.println("=== 3. Throwing Your Own Exceptions ===");
        try {
            int result = multiply(100, 200);
            System.out.println("100 * 200 = " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("CAUGHT: " + e.getMessage());
        }
        try {
            int result = multiply(900000000, 400000000);
            System.out.println("100 * 200 = " + result);
        } catch (IllegalArgumentException e) {
            System.out.println("CAUGHT IllegalArgumentException: " + e.getMessage());
        }
        System.out.println();

        // ---- 4. Custom Exceptions ----
        demonstrateCustomException();

//        // ---- 5. The finally Block ----
//        demonstrateFinally();
    }

    // =============================================================
    // 1. Catching a Checked Exception
    // =============================================================
    public static void readFile(String fileName) {
        try {
            FileReader reader = new FileReader(fileName);
            System.out.println("  File opened successfully: " + fileName);
        } catch (FileNotFoundException e) {
            System.out.println("  Error: File '" + fileName + "' not found");
        }
    }

    // =============================================================
    // 2. Catching an Unchecked Exception
    // =============================================================
    public static void printLength(String text) {

        try {
            System.out.println("  Length: " + text.length());
        } catch (NullPointerException e) {
            System.out.println("  Error: Cannot get length of null string");
        }
    }

    // =============================================================
    // 3. Throwing Your Own Exceptions
    // =============================================================
    public static int multiply(int a, int b) {
        if (a == 0 || b == 0) return 0;
        int product = a * b;
        if (product / a != b) {
            throw new IllegalArgumentException("Integer overflow: a * b exceeds int range");
        }
        return product;
    }

    // =============================================================
    // 4. Custom Exceptions
    // =============================================================
    private static void demonstrateCustomException() {
        System.out.println("=== 4. Custom Exceptions ===");

        BankAccount account = new BankAccount(100.0);
        System.out.println("  Starting balance: $" + account.getBalance());

        account.withdraw(30.0);
        System.out.println("  After withdrawing $30: $" + account.getBalance());

        try {
            account.withdraw(500.0);
        } catch (InsufficientFundsException e) {
            System.out.println("  CAUGHT InsufficientFundsException: " + e.getMessage());
            System.out.println("  Attempted amount: $" + e.getAmount());
        }

        System.out.println("  Balance unchanged after failed withdrawal: $" + account.getBalance());
        System.out.println();
    }

    // =============================================================
    // 5. The finally Block
    // =============================================================
    private static void demonstrateFinally() {
        System.out.println("=== 5. The finally Block ===");

        FileReader reader = null;
        try {
            reader = new FileReader("nonexistent.txt");
            System.out.println("  This line is never reached.");
        } catch (FileNotFoundException e) {
            System.out.println("  CAUGHT FileNotFoundException: " + e.getMessage());
        } finally {
            System.out.println("  FINALLY: Cleanup runs even after exception.");
            if (reader != null) {
                try { reader.close(); } catch (Exception ignored) { }
            }
        }
    }
}
