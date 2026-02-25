package com.ironhack.testing;

// Core JUnit 5 tests for CreditCard — covers @Test, assertEquals, @BeforeEach, edge cases.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreditCardTest {

    private CreditCard card;

    @BeforeEach
    void setUp() {
        card = new CreditCard(100);
    }

    // --- Basic assertions ---

    @Test
    void addBalance_normalAmount_balanceIncreases() {
        card.addBalance(200);
        assertEquals(300, card.getBalance());
    }

    @Test
    void getBalance_afterConstruction_returnsInitialBalance() {
        assertEquals(100, card.getBalance());
    }

    @Test
    void getBalanceLimit_returnsLimit() {
        assertEquals(1000, card.getBalanceLimit());
    }

    // --- Edge cases ---

    @Test
    void addBalance_exceedsLimit_balanceUnchanged() {
        card.addBalance(1000);
        assertEquals(100, card.getBalance());
    }

    @Test
    void addBalance_exactlyAtLimit_balanceUnchanged() {
        card.addBalance(900);
        assertEquals(100, card.getBalance());
    }

    // --- Multiple assertion types ---

    @Test
    void addBalance_resultAboveZero_balanceIsPositive() {
        card.addBalance(50);
        assertTrue(card.getBalance() > 0);
        assertFalse(card.getBalance() < 0);
    }

    // --- isLowBalance ---

    @Test
    void isLowBalance_below10Percent_returnsTrue() {
        CreditCard lowCard = new CreditCard(50);
        assertTrue(lowCard.isLowBalance());
    }

    @Test
    void isLowBalance_wellAboveThreshold_returnsFalse() {
        CreditCard richCard = new CreditCard(500);
        assertFalse(richCard.isLowBalance());
    }
}
