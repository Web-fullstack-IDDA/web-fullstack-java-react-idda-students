package com.ironhack.interfaces;

// Child class that extends CreditCard, removing the limit and adding a points system.
public class RewardCard extends CreditCard {

    private int points;

    public RewardCard(double balance, int points) {
        super(balance);
        this.points = points;
    }

    @Override
    public void addBalance(double amount) {
        balance += amount;
        System.out.println("RewardCard: Added " + amount + " (no limit!) -> balance is now " + balance);
    }

    public void applyPoints() {
        double bonus = points * 0.05;
        balance += bonus;
        System.out.println("RewardCard: Applied " + points + " points (+" + bonus + ") -> balance is now " + balance);
        points = 0;
    }

    public int getPoints() {
        return points;
    }
}
