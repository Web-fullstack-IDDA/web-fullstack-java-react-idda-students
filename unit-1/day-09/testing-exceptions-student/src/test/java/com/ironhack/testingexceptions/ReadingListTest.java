package com.ironhack.testingexceptions;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ReadingListTest {

    @Test
    public void addBook_addsBookToList() {
        ReadingList rList = new ReadingList();
        rList.addBook(new Book(6757, "Don Quixote", "Miguel de Cervantes", 1605));

        // Different ISBN, same content -- equals returns true because isbn is excluded
        Book expected = new Book(5324, "Don Quixote", "Miguel de Cervantes", 1605);
        assertEquals(expected, rList.getReadingList().get(0));
    }
}
