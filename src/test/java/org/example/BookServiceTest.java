package org.example;

import org.example.Book;
import org.example.BookService;
import org.example.User;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class BookServiceTest {

    private BookService bookService;
    private Book mockBook;
    private User mockUser;

    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("Starting BookService tests...");
    }

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
        mockBook = mock(Book.class);
        mockUser = mock(User.class);

        when(mockBook.getTitle()).thenReturn("Test Title");
        when(mockBook.getAuthor()).thenReturn("Test Author");
        when(mockBook.getGenre()).thenReturn("Test Genre");

        when(mockUser.getPurchasedBooks()).thenReturn(new ArrayList<>());
    }

    @Test
    public void testSearchBook_PositiveCase() {
        bookService.addBook(mockBook);
        List<Book> result = bookService.searchBook("Test Title");
        assertFalse(result.isEmpty());
        assertEquals(mockBook, result.get(0));
    }

    @Test
    public void testSearchBook_NegativeCase() {
        List<Book> result = bookService.searchBook("Nonexistent Title");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSearchBook_EdgeCase() {
        List<Book> result = bookService.searchBook("");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPurchaseBook_PositiveCase() {
        bookService.addBook(mockBook);
        boolean result = bookService.purchaseBook(mockUser, mockBook);
        assertTrue(result);
    }

    @Test
    public void testPurchaseBook_NegativeCase() {
        boolean result = bookService.purchaseBook(mockUser, mockBook);
        assertFalse(result);
    }

    @Test
    public void testPurchaseBook_EdgeCase() {
        boolean result = bookService.purchaseBook(null, mockBook);
        assertFalse(result);
    }

    @AfterEach
    public void tearDown() {
        bookService = null;
        mockBook = null;
        mockUser = null;
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("BookService tests completed.");
    }
}

