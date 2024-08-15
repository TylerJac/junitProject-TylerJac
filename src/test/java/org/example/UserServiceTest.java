package org.example;
import org.example.User;
import org.example.UserService;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

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
    public void testRegisterUser_EdgeCase() {
        User emptyUsernameUser = new User("", "password", "test@example.com");
        boolean result = userService.registerUser(emptyUsernameUser);
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
    
}
