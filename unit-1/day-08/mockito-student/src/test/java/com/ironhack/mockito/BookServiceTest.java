package com.ironhack.mockito;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

// Inform/Extend JUnit to initialize Mockito
@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    // Create a fake repository
    @Mock
    private BookRepository bookRepository;

    // Pass the mock repository to the BookService Constructor and instantiate it
    @InjectMocks
    private BookService bookService;

    @Test
    public void getBookById_bookExists_returnsBook() {
        // Arrange
        Book book = new Book(1L, "Clean Code", "Robert C. Martin");
        // stub (replaces the behaviour of the repository)
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        // Act
        Book result = bookService.getBookById(1L);

        // Assert
        assertEquals("Clean Code", result.getTitle());
        assertEquals("Robert C. Martin", result.getAuthor());
    }

    @Test
    public void getBookById_bookNotFound_throwsException() {

        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.getBookById(999L);
        });
    }

    @Test
    public void getAllBooks_returnsAllBooks() {
        Book book1 = new Book(1L, "Clean Code", "Robert C. Martin");
        Book book2 = new Book(2L, "Effective Java", "Joshua Bloch");
        when(bookRepository.findAll()).thenReturn(List.of(book1, book2));

        List<Book> result = bookService.getAllBooks();

        assertEquals(2, result.size());
        assertEquals("Clean Code", result.get(0).getTitle());
        assertEquals("Effective Java", result.get(1).getTitle());
    }

    @Test
    public void removeBook_bookExists_deletesBook() {
        Book book = new Book(1L, "Old Book", "Some Author");
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        bookService.removeBook(1L);

        verify(bookRepository, times(1)).deleteById(1L);
    }

    @Test
    public void removeBook_bookNotFound_neverDeletes() {
        when(bookRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(IllegalArgumentException.class, () -> {
            bookService.removeBook(999L);
        });

        verify(bookRepository, never()).deleteById(999L);
    }

    @Test
    public void addBook_validBook_returnsBook() {
        Book newBook = new Book(null, "New Book", "New Author");
        Book savedBook = new Book(3L, "New Book", "New Author");
        when(bookRepository.save(newBook)).thenReturn(savedBook);

        Book result = bookService.addBook(newBook);

        assertEquals(3L, result.getId());
        assertEquals("New Book", result.getTitle());
        verify(bookRepository, times(1)).save(newBook);
    }
}
