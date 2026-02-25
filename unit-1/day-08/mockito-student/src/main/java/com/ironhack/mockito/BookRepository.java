package com.ironhack.mockito;

import java.util.List;
import java.util.Optional;

// Interface for book data access — this is the dependency we will mock in tests.
public interface BookRepository {

    Optional<Book> findById(Long id);

    List<Book> findAll();

    Book save(Book book);

    void deleteById(Long id);
}
