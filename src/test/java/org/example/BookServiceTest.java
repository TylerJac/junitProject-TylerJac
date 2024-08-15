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

    // This method runs once before all tests
    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("Starting BookService tests...");
    }

    // This method runs before each test, setting up common objects and mocks
    @BeforeEach
    public void setUp() {
        bookService = new BookService(); // Initialize the BookService instance
        mockBook = mock(Book.class); // Create a mock Book object
        mockUser = mock(User.class); // Create a mock User object

        // Mocking book properties
        when(mockBook.getTitle()).thenReturn("Test Title");
        when(mockBook.getAuthor()).thenReturn("Test Author");
        when(mockBook.getGenre()).thenReturn("Test Genre");

        // Mocking user's purchased books as an empty list
        when(mockUser.getPurchasedBooks()).thenReturn(new ArrayList<>());
    }

    // Test case for a positive scenario in the searchBook method
    @Test
    public void testSearchBook_PositiveCase() {
        bookService.addBook(mockBook); // Adding the mock book to the database
        List<Book> result = bookService.searchBook("Test Title"); // Searching for the book by title
        assertFalse(result.isEmpty()); // Ensure the result is not empty
        assertEquals(mockBook, result.get(0)); // Verify the correct book is returned
    }

    // Test case for a negative scenario in the searchBook method
    @Test
    public void testSearchBook_NegativeCase() {
        List<Book> result = bookService.searchBook("Nonexistent Title"); // Searching for a non-existent title
        assertTrue(result.isEmpty()); // The result should be empty
    }

    // Test case for an edge scenario in the searchBook method (searching with an empty keyword)
    @Test
    public void testSearchBook_EdgeCase() {
        List<Book> result = bookService.searchBook(""); // Searching with an empty string
        assertTrue(result.isEmpty()); // The result should be empty
    }

    // Test case for a positive scenario in the purchaseBook method
    @Test
    public void testPurchaseBook_PositiveCase() {
        bookService.addBook(mockBook); // Adding the mock book to the database
        boolean result = bookService.purchaseBook(mockUser, mockBook); // Attempting to purchase the book
        assertTrue(result); // The purchase should be successful
    }

    // Test case for a negative scenario in the purchaseBook method (book not in database)
    @Test
    public void testPurchaseBook_NegativeCase() {
        boolean result = bookService.purchaseBook(mockUser, mockBook); // Attempting to purchase a book not in the database
        assertFalse(result); // The purchase should fail
    }

    // Test case for an edge scenario in the purchaseBook method (null user)
    @Test
    public void testPurchaseBook_EdgeCase() {
        boolean result = bookService.purchaseBook(null, mockBook); // Attempting to purchase with a null user
        assertFalse(result); // The purchase should fail
    }

    // This method runs after each test, cleaning up resources
    @AfterEach
    public void tearDown() {
        bookService = null; // Set bookService to null to clean up
        mockBook = null; // Set mockBook to null to clean up
        mockUser = null; // Set mockUser to null to clean up
    }

    // This method runs once after all tests
    @AfterAll
    public static void afterAllTests() {
        System.out.println("BookService tests completed.");
    }

    // Test case for a positive scenario in the addBookReview method
    @Test
    public void testAddBookReview_PositiveCase() {
        Book realBook = new Book("Title", "Author", "Genre", 22.33); // Create a real Book instance
        bookService.addBook(realBook); // Add the real book to the database
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(realBook)); // Mock that the user has purchased the book
        boolean result = bookService.addBookReview(mockUser, realBook, "Great book!"); // Add a review
        assertTrue(result); // The review should be added successfully
        assertEquals(1, realBook.getReviews().size()); // Ensure there's one review
        assertEquals("Great book!", realBook.getReviews().get(0)); // The review content should be as expected
    }

    // Test case for a negative scenario in the addBookReview method (user has not purchased the book)
    @Test
    public void testAddBookReview_NegativeCase() {
        bookService.addBook(mockBook); // Add the mock book to the database
        when(mockUser.getPurchasedBooks()).thenReturn(new ArrayList<>()); // Mock that the user hasn't purchased any books
        boolean result = bookService.addBookReview(mockUser, mockBook, "Great book!"); // Attempt to add a review
        assertFalse(result); // The review should not be added
        assertTrue(mockBook.getReviews().isEmpty()); // The reviews list should still be empty
    }

    // Test case for an edge scenario in the addBookReview method (empty review content)
    @Test
    public void testAddBookReview_EdgeCase() {
        List<String> mockReviews = new ArrayList<>(); // Create a mock reviews list
        when(mockBook.getReviews()).thenReturn(mockReviews); // Mock the book's reviews list
        when(mockUser.getPurchasedBooks()).thenReturn(List.of(mockBook)); // Mock that the user has purchased the book
        boolean result = bookService.addBookReview(mockUser, mockBook, ""); // Attempt to add an empty review
        assertTrue(result); // The review should be added successfully
        assertEquals(1, mockReviews.size()); // Ensure there's one review
        assertEquals("", mockReviews.get(0)); // The review content should be empty
    }
}
