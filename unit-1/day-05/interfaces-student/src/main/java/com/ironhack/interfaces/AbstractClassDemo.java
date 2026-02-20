package com.ironhack.interfaces;

// Demonstrates abstract classes, polymorphism, and when to choose abstract class vs interface.
public class AbstractClassDemo {

    public static void run() {
        System.out.println("=== ABSTRACT CLASS DEMO ===\n");

        // Cannot instantiate abstract class:
        // Account a = new Account(1000);  // Compile error -- abstract class

        // Variable type is Account (abstract); actual objects are concrete subclasses.
        System.out.println("--- Creating accounts ---");
        Account savings  = new SavingsAccount(1000);
        Account checking = new CheckingAccount(500, 200);

        // processCredit: both subclasses add to the balance.
        System.out.println("--- processCredit(500) on both accounts ---");
        savings.processCredit(500);
        checking.processCredit(500);
        System.out.println();

        // processDebit: same call, different rules per subclass — polymorphism.
        System.out.println("--- processDebit(1800) on both accounts ---");
        savings.processDebit(1800);
        checking.processDebit(1800);
        System.out.println();

        // Overdraft in action: 1100 <= 1000 + 200, so CheckingAccount accepts.
        System.out.println("--- processDebit(1100) on checking (overdraft allowed) ---");
        checking.processDebit(1100);
        System.out.println();

        // getBalance() is a concrete method defined once in Account — inherited by both.
        System.out.println("--- getBalance() — concrete method shared by all subclasses ---");
        System.out.println("Savings balance:  " + savings.getBalance());
        System.out.println("Checking balance: " + checking.getBalance());
        System.out.println();

        // Any Account subclass can go into an Account[] array.
        System.out.println("--- Account[] polymorphic array ---");
        Account[] accounts = {
                new SavingsAccount(2000),
                new CheckingAccount(800, 300)
        };

        for (Account account : accounts) {
            account.processCredit(100);
        }
        System.out.println();

    }
}
