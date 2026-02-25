package com.ironhack.testing;

/**
 * A credit card with a fixed balance limit of 1000.
 * Adding balance is only allowed when the result stays strictly below the limit.
 */
public class CreditCard {

    private final double BALANCE_LIMIT = 1000;
    protected double balance;

    public CreditCard(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        if (balance + amount < BALANCE_LIMIT) {
            balance += amount;
        }
    }

    public double getBalance() {
        return balance;
    }

    public double getBalanceLimit() {
        return BALANCE_LIMIT;
    }

    public boolean isLowBalance() {
        return balance < BALANCE_LIMIT * 0.1;
    }
}
