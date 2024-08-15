package org.example;

import org.junit.jupiter.api.*;
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

    @Test
    public void testAddBookReview_PositiveCase() {
        Book realBook = new Book("Title", "Author", "Genre", 22.33);
        bookService.addBook(realBook);
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(realBook));
        boolean result = bookService.addBookReview(mockUser, realBook, "Great book!");
        assertTrue(result);
        assertEquals(1, realBook.getReviews().size());
        assertEquals("Great book!", realBook.getReviews().get(0));
    }

    @Test
    public void testAddBookReview_NegativeCase() {
        bookService.addBook(mockBook);
        when(mockUser.getPurchasedBooks()).thenReturn(new ArrayList<>());
        boolean result = bookService.addBookReview(mockUser, mockBook, "Great book!");
        assertFalse(result);
        assertTrue(mockBook.getReviews().isEmpty());
    }

    @Test
    public void testAddBookReview_EdgeCase() {
        List<String> mockReviews = new ArrayList<>();
        when(mockBook.getReviews()).thenReturn(mockReviews);
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(mockBook));
        boolean result = bookService.addBookReview(mockUser, mockBook, "");
        assertTrue(result);
        assertEquals(1, mockReviews.size());
        assertEquals("", mockReviews.get(0));
    }
}