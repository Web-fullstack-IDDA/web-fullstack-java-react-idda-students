package com.ironhack.interfaces;

// Parent class representing a basic credit card with a spending limit.
public class CreditCard {

    private static final double LIMIT = 1000;
    protected double balance;

    public CreditCard(double balance) {
        this.balance = balance;
    }

    public void addBalance(double amount) {
        if (balance + amount < LIMIT) {
            balance += amount;
            System.out.println("CreditCard: Added " + amount + " -> balance is now " + balance);
        } else {
            System.out.println("CreditCard: Rejected! " + amount + " would exceed the limit of " + LIMIT);
        }
    }

    public double getBalance() {
        return balance;
    }
}
