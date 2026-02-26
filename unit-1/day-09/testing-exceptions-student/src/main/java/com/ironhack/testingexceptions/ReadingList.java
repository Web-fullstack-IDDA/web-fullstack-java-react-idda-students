package com.ironhack.testingexceptions;

import java.util.ArrayList;
import java.util.List;

public class ReadingList {

    private final List<Book> readingList = new ArrayList<>();

    // Adds a book to the list; throws IllegalArgumentException if book is null
    public void addBook(Book book) {
        if (book == null) {
            throw new IllegalArgumentException("Book cannot be null");
        }
        readingList.add(book);
    }

    public List<Book> getReadingList() {
        return readingList;
    }

    public int getBookCount() {
        return readingList.size();
    }
}
