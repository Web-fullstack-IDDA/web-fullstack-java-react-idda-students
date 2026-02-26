package com.ironhack.exceptions;

// Bank account that throws InsufficientFundsException when a withdrawal exceeds the balance.
public class BankAccount {

    private double balance;

    public BankAccount(double balance) {
        this.balance = balance;
    }

    public void withdraw(double amount) {
        if (amount > balance) {
            throw new InsufficientFundsException(
                    "Cannot withdraw " + amount + " with balance " + balance,
                    amount
            );
        }
        balance -= amount;
    }

    public double getBalance() {
        return balance;
    }
}
