package com.ironhack.interfaces;

// Concrete subclass of Account. Allows overdraft up to a configurable limit.
public class CheckingAccount extends Account {

    // CheckingAccount-specific field — Account doesn't have this.
    private double overdraftLimit;

    public CheckingAccount(double balance, double overdraftLimit) {
        super(balance);
        this.overdraftLimit = overdraftLimit;
    }

    // @Override is not mandatory when implementing abstract methods, but always
    // write it -- it protects against typos and makes the intent clear.

    // Allows debit up to balance + overdraftLimit. Rejects anything beyond that.
    @Override
    public void processDebit(double amount) {
        if (amount > getBalance() + overdraftLimit) {
            System.out.println("CheckingAccount: Rejected debit of " + amount
                    + " — exceeds overdraft limit (balance: " + getBalance()
                    + ", limit: " + overdraftLimit + ")");
        } else {
            setBalance(getBalance() - amount);
            System.out.println("CheckingAccount: Debited " + amount
                    + " -> balance is now " + getBalance());
        }
    }

    @Override
    public void processCredit(double amount) {
        setBalance(getBalance() + amount);
        System.out.println("CheckingAccount: Credited " + amount
                + " -> balance is now " + getBalance());
    }

    public double getOverdraftLimit() {
        return overdraftLimit;
    }
}
