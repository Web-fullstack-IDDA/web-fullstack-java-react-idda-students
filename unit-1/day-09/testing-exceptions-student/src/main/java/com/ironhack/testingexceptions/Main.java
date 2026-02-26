package com.ironhack.testingexceptions;

public class Main {
    public static void main(String[] args) {

        // Two books with identical data but different isbn values.
        Book book1 = new Book(1L, "Clean Code", "Robert C. Martin", 2008);
        Book book2 = new Book(2L, "Clean Code", "Robert C. Martin", 2008);

        // Without an === equals() override === Java compares object references, not field values.
        // Two distinct objects always return false, even if they hold the same data.
        // Overriding equals() to compare title, author, and publishYear returns true here.
        System.out.println(book1.equals(book2));

        // The === equals/hashCode contract === requires that if equals() returns true,
        // both objects must produce the same hashCode. Breaking this causes silent bugs
        // in HashMap and HashSet, where objects are first located by bucket (hashCode)
        // and only then confirmed by equals(). Both methods must use the same fields.

        // Without a === toString() override ===, Java prints <ClassName>@<hashCode in hex> (e.g. Book@6d06d69c).
        // Overriding toString() makes output human-readable and test failure messages useful.
        System.out.println(book1.toString());
        System.out.println(book2.toString());
    }
}
