package com.ironhack.interfaces;

// Concrete subclass of Account. Rejects debits that exceed the balance.
public class SavingsAccount extends Account {

    // Calls super(balance) to let Account initialize the shared balance field.
    public SavingsAccount(double balance) {
        super(balance);
    }

    // @Override is not mandatory when implementing abstract methods, but always
    // write it -- it protects against typos and makes the intent clear.

    @Override
    public void processDebit(double amount) {
        if (amount > getBalance()) {
            System.out.println("SavingsAccount: Rejected debit of " + amount
                    + " — insufficient funds (balance: " + getBalance() + ")");
        } else {
            setBalance(getBalance() - amount);
            System.out.println("SavingsAccount: Debited " + amount
                    + " -> balance is now " + getBalance());
        }
    }

    @Override
    public void processCredit(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("SavingsAccount: Credited " + amount
                + " -> balance is now " + getBalance());
    }
}
