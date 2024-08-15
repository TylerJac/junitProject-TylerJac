package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private User mockUser;

    // This method runs once before all tests in this class
    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("Starting UserService tests...");
    }

    // This method runs before each test, setting up the UserService and mock User
    @BeforeEach
    public void setUp() {
        userService = new UserService(); // Initialize the UserService instance
        mockUser = mock(User.class); // Create a mock User object
        // Set up mock behavior for the User object
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getPassword()).thenReturn("password");
        when(mockUser.getEmail()).thenReturn("test@example.com");
    }

    // Test case for a positive scenario in the registerUser method
    @Test
    public void testRegisterUser_PositiveCase() {
        boolean result = userService.registerUser(mockUser); // Attempt to register the mock user
        assertTrue(result); // The registration should be successful
    }

    // Test case for a negative scenario in the registerUser method (duplicate registration)
    @Test
    public void testRegisterUser_NegativeCase() {
        userService.registerUser(mockUser); // Register the mock user first time
        boolean result = userService.registerUser(mockUser); // Attempt to register the same user again
        assertFalse(result); // The registration should fail as the user already exists
    }

    // Test case for a positive scenario in the loginUser method
    @Test
    public void testLoginUser_PositiveCase() {
        userService.registerUser(mockUser); // Register the mock user
        User result = userService.loginUser("testUser", "password"); // Attempt to login with correct credentials
        assertNotNull(result); // The login should be successful, returning a non-null user object
    }

    // Test case for a negative scenario in the loginUser method (wrong password)
    @Test
    public void testLoginUser_NegativeCase() {
        userService.registerUser(mockUser); // Register the mock user
        User result = userService.loginUser("testUser", "wrongpassword"); // Attempt to login with incorrect password
        assertNull(result); // The login should fail, returning null
    }

    // Test case for an edge scenario in the loginUser method (non-existent user)
    @Test
    public void testLoginUser_EdgeCase() {
        User result = userService.loginUser("nonexistentUser", "password"); // Attempt to login with a non-existent user
        assertNull(result); // The login should fail, returning null
    }

    // This method runs after each test, cleaning up resources
    @AfterEach
    public void tearDown() {
        userService = null; // Set userService to null to clean up
        mockUser = null; // Set mockUser to null to clean up
    }

    // This method runs once after all tests in this class
    @AfterAll
    public static void afterAllTests() {
        System.out.println("UserService tests completed.");
    }

    // Test case for a positive scenario in the updateUserProfile method
    @Test
    public void testUpdateUserProfile_PositiveCase() {
        User mockUser = new User("originalUsername", "originalPassword", "original@example.com"); // Create a new User instance
        userService.registerUser(mockUser); // Register the user
        boolean result = userService.updateUserProfile(mockUser, "newUsername", "newPassword", "new@example.com"); // Update user profile
        assertTrue(result); // The profile update should be successful
        assertNull(userService.loginUser("originalUsername", "originalPassword")); // The old credentials should no longer work
        User updatedUser = userService.loginUser("newUsername", "newPassword"); // Attempt to login with new credentials
        assertNotNull(updatedUser); // The login should be successful with new credentials
        // Verify that the user's details were updated correctly
        assertEquals("newUsername", updatedUser.getUsername());
        assertEquals("newPassword", updatedUser.getPassword());
        assertEquals("new@example.com", updatedUser.getEmail());
    }

    // Test case for a negative scenario in the updateUserProfile method (username already taken)
    @Test
    public void testUpdateUserProfile_NegativeCase() {
        userService.registerUser(mockUser); // Register the mock user
        User anotherUser = new User("anotherUser", "password", "another@example.com"); // Create another User instance
        userService.registerUser(anotherUser); // Register the other user
        boolean result = userService.updateUserProfile(mockUser, "anotherUser", "newPassword", "new@example.com"); // Attempt to update profile with a taken username
        assertFalse(result); // The update should fail as the new username is already taken
        assertNotEquals("anotherUser", mockUser.getUsername()); // The original user's username should remain unchanged
    }

    // Test case for an edge scenario in the updateUserProfile method (empty new credentials)
    @Test
    public void testUpdateUserProfile_EdgeCase() {
        userService.registerUser(mockUser); // Register the mock user
        boolean result = userService.updateUserProfile(mockUser, "", "", ""); // Attempt to update with empty credentials
        assertTrue(result); // The update should be successful
    }
}
