package org.example;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {

    private UserService userService;
    private User mockUser;

    @BeforeAll
    public static void beforeAllTests() {
        System.out.println("Starting UserService tests...");
    }

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        mockUser = mock(User.class);
        when(mockUser.getUsername()).thenReturn("testUser");
        when(mockUser.getPassword()).thenReturn("password");
        when(mockUser.getEmail()).thenReturn("test@example.com");
    }

    @Test
    public void testRegisterUser_PositiveCase() {
        boolean result = userService.registerUser(mockUser);
        assertTrue(result);
    }

    @Test
    public void testRegisterUser_NegativeCase() {
        userService.registerUser(mockUser); // Register first time
        boolean result = userService.registerUser(mockUser); // Attempt to register again
        assertFalse(result);
    }

    @Test
    public void testLoginUser_PositiveCase() {
        userService.registerUser(mockUser);
        User result = userService.loginUser("testUser", "password");
        assertNotNull(result);
    }

    @Test
    public void testLoginUser_NegativeCase() {
        userService.registerUser(mockUser);
        User result = userService.loginUser("testUser", "wrongpassword");
        assertNull(result);
    }

    @Test
    public void testLoginUser_EdgeCase() {
        User result = userService.loginUser("nonexistentUser", "password");
        assertNull(result);
    }

    @AfterEach
    public void tearDown() {
        userService = null;
        mockUser = null;
    }

    @AfterAll
    public static void afterAllTests() {
        System.out.println("UserService tests completed.");
    }

    @Test
    public void testUpdateUserProfile_PositiveCase() {
        User mockUser = new User("originalUsername", "originalPassword", "original@example.com");
        userService.registerUser(mockUser);
        boolean result = userService.updateUserProfile(mockUser, "newUsername", "newPassword", "new@example.com");
        assertTrue(result);
        assertNull(userService.loginUser("originalUsername", "originalPassword"));
        User updatedUser = userService.loginUser("newUsername", "newPassword");
        assertNotNull(updatedUser);
        assertEquals("newUsername", updatedUser.getUsername());
        assertEquals("newPassword", updatedUser.getPassword());
        assertEquals("new@example.com", updatedUser.getEmail());
    }

    @Test
    public void testUpdateUserProfile_NegativeCase() {
        userService.registerUser(mockUser);
        User anotherUser = new User("anotherUser", "password", "another@example.com");
        userService.registerUser(anotherUser);
        boolean result = userService.updateUserProfile(mockUser, "anotherUser", "newPassword", "new@example.com");
        assertFalse(result);
        assertNotEquals("anotherUser", mockUser.getUsername());
    }

    @Test
    public void testUpdateUserProfile_EdgeCase() {
        userService.registerUser(mockUser);
        boolean result = userService.updateUserProfile(mockUser, "", "", "");
        assertTrue(result);
    }
}