package com.ironhack.testing;

// Tests for BankAccount — focused on assertThrows for exception testing.

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BankAccountTest {

    private BankAccount account;

    @BeforeEach
    void setUp() {
        account = new BankAccount(100);
    }

    // --- Happy path ---

    @Test
    void withdraw_validAmount_reducesBalance() {
        account.withdraw(30);
        assertEquals(70, account.getBalance());
    }

    @Test
    void withdraw_exactBalance_balanceBecomesZero() {
        account.withdraw(100);
        assertEquals(0, account.getBalance());
    }

    // --- Exception testing ---

    @Test
    void withdraw_insufficientFunds_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(500);
        });
    }

    @Test
    void withdraw_amountJustOverBalance_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(101);
        });
    }

    // --- State consistency ---

    @Test
    void withdraw_insufficientFunds_balanceUnchanged() {
        assertThrows(IllegalArgumentException.class, () -> {
            account.withdraw(500);
        });
        assertEquals(100, account.getBalance());
    }
}
