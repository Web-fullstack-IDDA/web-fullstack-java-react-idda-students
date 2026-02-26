package com.ironhack.testingexceptions;

import java.util.Objects;

public class Book {

    private long isbn;
    private String title;
    private String author;
    private int publishYear;

    public Book(long isbn, String title, String author, int publishYear) {
        this.isbn = isbn;
        this.title = title;
        this.author = author;
        this.publishYear = publishYear;
    }

    public long getIsbn() { return isbn; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublishYear() { return publishYear; }

    // isbn is excluded (id DB key, not part of logical identity)
    @Override
    public boolean equals(Object o) {
        // same reference in memory, no need to compare fields
        if (this == o) return true;
        // Pattern-matching instanceof: null check, type check, and cast in one expression.
        // If o is not a Book (or is null), the condition is false.
        if (!(o instanceof Book book)) return false;
        return publishYear == book.publishYear &&
               Objects.equals(title, book.title) &&
               Objects.equals(author, book.author);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, publishYear);
    }

    @Override
    public String toString() {
        return "Book{title='%s', author='%s', publishYear=%d}".formatted(title, author, publishYear);
    }
}
