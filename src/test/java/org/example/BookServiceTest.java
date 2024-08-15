package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class BookServiceTest {

    private BookService bookService;
    private Book testBook;
    private User testUser;

    @BeforeEach
    public void setUp() {
        bookService = new BookService();
        testBook = new Book("Test Title", "Test Author", "Test Genre", 29.99);
        testUser = new User("testUser", "password", "test@example.com");
    }

    @Test
    public void testSearchBook_BookExists_ShouldReturnBookList() {
        bookService.addBook(testBook);
        List<Book> result = bookService.searchBook("Test Title");
        assertFalse(result.isEmpty());
        assertEquals(1, result.size());
        assertEquals(testBook, result.get(0));
    }

    @Test
    public void testSearchBook_BookNotExists_ShouldReturnEmptyList() {
        List<Book> result = bookService.searchBook("Nonexistent Title");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testPurchaseBook_BookExists_ShouldReturnTrue() {
        bookService.addBook(testBook);
        boolean result = bookService.purchaseBook(testUser, testBook);
        assertTrue(result);
    }

    @Test
    public void testPurchaseBook_BookNotExists_ShouldReturnFalse() {
        boolean result = bookService.purchaseBook(testUser, testBook);
        assertFalse(result);
    }

    @Test
    public void testAddBookReview_UserHasPurchasedBook_ShouldReturnTrue() {
        bookService.addBook(testBook);
        testUser.getPurchasedBooks().add(testBook);
        boolean result = bookService.addBookReview(testUser, testBook, "Great book!");
        assertTrue(result);
    }

    @Test
    public void testAddBookReview_UserHasNotPurchasedBook_ShouldReturnFalse() {
        bookService.addBook(testBook);
        boolean result = bookService.addBookReview(testUser, testBook, "Great book!");
        assertFalse(result);
    }

    @Test
    public void testAddBook_NewBook_ShouldReturnTrue() {
        boolean result = bookService.addBook(testBook);
        assertTrue(result);
    }

    @Test
    public void testAddBook_BookAlreadyExists_ShouldReturnFalse() {
        bookService.addBook(testBook);
        boolean result = bookService.addBook(testBook);
        assertFalse(result);
    }

    @Test
    public void testRemoveBook_BookExists_ShouldReturnTrue() {
        bookService.addBook(testBook);
        boolean result = bookService.removeBook(testBook);
        assertTrue(result);
    }

    @Test
    public void testRemoveBook_BookNotExists_ShouldReturnFalse() {
        boolean result = bookService.removeBook(testBook);
        assertFalse(result);
    }
}

