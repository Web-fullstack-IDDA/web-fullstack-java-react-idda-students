package com.ironhack.interfaces;

// Demonstrates inheritance, method overriding, and polymorphism using credit cards.
public class InheritanceDemo {

    public static void run() {
        System.out.println("=== INHERITANCE & POLYMORPHISM DEMO ===\n");

        CreditCard card = new CreditCard(100);
        RewardCard reward = new RewardCard(100, 200);

        System.out.println("--- addBalance(2000) on both cards ---");
        card.addBalance(2000);
        reward.addBalance(2000);
        System.out.println();

        System.out.println("--- Balances after addBalance ---");
        System.out.println("CreditCard balance: " + card.getBalance());
        System.out.println("RewardCard balance: " + reward.getBalance());
        System.out.println();

        System.out.println("--- RewardCard-specific: applyPoints() ---");
        reward.applyPoints();
        System.out.println("RewardCard balance after points: " + reward.getBalance());
        System.out.println();

        System.out.println("--- Polymorphic array (CreditCard[]) ---");
        CreditCard[] wallet = {
                new CreditCard(500),
                new RewardCard(300, 100)
        };

        for (CreditCard c : wallet) {
            c.addBalance(600);
        }

        System.out.println();
        for (CreditCard c : wallet) {
            System.out.println("  Balance: " + c.getBalance());
        }

        System.out.println();
    }
}
