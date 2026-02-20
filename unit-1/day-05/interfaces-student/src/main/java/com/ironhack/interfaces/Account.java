package com.ironhack.interfaces;

// Abstract class representing a generic bank account.
//
// When to use an abstract class (vs an interface):
//   - Use an ABSTRACT CLASS when subclasses share STATE (fields like balance)
//     and some concrete behavior, plus each subclass must implement its own rules.
//   - Use an INTERFACE when unrelated classes share a capability with no shared state.
//   - Rule of thumb: "Can do" = interface.  "Is a (with shared data)" = abstract class.
//
// An abstract class cannot be instantiated directly.
// "new Account(1000)" is a compile error — use a concrete subclass instead.
public abstract class Account {

    // Shared state — every account type has a balance.
    // Interfaces cannot have instance fields like this.
    private double balance;

    // Called by subclasses via super(balance). Cannot be called as "new Account()".
    public Account(double balance) {
        this.balance = balance;
    }

    // Abstract methods — no body. Subclasses MUST implement them.
    // Each account type has different debit/credit rules.
    public abstract void processDebit(double amount);

    public abstract void processCredit(double amount);

    // Concrete methods — shared by all subclasses, no need to rewrite.
    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }
}
