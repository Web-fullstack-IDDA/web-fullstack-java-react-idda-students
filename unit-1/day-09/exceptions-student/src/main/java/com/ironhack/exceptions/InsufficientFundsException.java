package com.ironhack.exceptions;

// Custom unchecked exception thrown when a withdrawal exceeds the account balance.
// Carries the attempted withdrawal amount as extra context.
public class InsufficientFundsException extends RuntimeException {

    private final double amount;

    public InsufficientFundsException(String message, double amount) {
        super(message);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}
