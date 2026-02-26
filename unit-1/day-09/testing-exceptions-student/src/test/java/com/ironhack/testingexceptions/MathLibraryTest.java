package com.ironhack.testingexceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MathLibraryTest {

    @Test
    public void multiply_overflow_throwsException() {
        assertThrows(IllegalArgumentException.class, () -> {
            MathLibrary.multiply(900000000, 400000000);
        });
    }

    @Test
    public void multiply_overflow_throwsWithCorrectMessage() {
        IllegalArgumentException exception = assertThrows(
            IllegalArgumentException.class,
            () -> MathLibrary.multiply(900000000, 400000000)
        );
        assertEquals("Product of input is too great for int", exception.getMessage());
    }

    @Test
    public void multiply_smallInts_doesNotThrow() {
        int result = assertDoesNotThrow(() -> MathLibrary.multiply(10, 20));
        assertEquals(200, result);
    }
}
