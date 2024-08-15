package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class UserServiceTest {

    private UserService userService;
    private User testUser;

    @BeforeEach
    public void setUp() {
        userService = new UserService();
        testUser = new User("testUser", "password", "test@example.com");
    }

    @Test
    public void testRegisterUser_NewUser_ShouldReturnTrue() {
        boolean result = userService.registerUser(testUser);
        assertTrue(result);
    }

    @Test
    public void testRegisterUser_UserAlreadyExists_ShouldReturnFalse() {
        userService.registerUser(testUser);
        boolean result = userService.registerUser(testUser);
        assertFalse(result);
    }

    @Test
    public void testLoginUser_ValidCredentials_ShouldReturnUser() {
        userService.registerUser(testUser);
        User result = userService.loginUser("testUser", "password");
        assertNotNull(result);
    }

    @Test
    public void testLoginUser_InvalidUsername_ShouldReturnNull() {
        User result = userService.loginUser("nonexistentUser", "password");
        assertNull(result);
    }

    @Test
    public void testLoginUser_InvalidPassword_ShouldReturnNull() {
        userService.registerUser(testUser);
        User result = userService.loginUser("testUser", "wrongpassword");
        assertNull(result);
    }

    @Test
    public void testUpdateUserProfile_ValidUpdate_ShouldReturnTrue() {
        userService.registerUser(testUser);
        boolean result = userService.updateUserProfile(testUser, "newUser", "newPassword", "new@example.com");
        assertTrue(result);
    }

    @Test
    public void testUpdateUserProfile_UsernameTaken_ShouldReturnFalse() {
        userService.registerUser(testUser);
        User anotherUser = new User("newUser", "password", "new@example.com");
        userService.registerUser(anotherUser);
        boolean result = userService.updateUserProfile(testUser, "newUser", "newPassword", "new2@example.com");
        assertFalse(result);
    }
}

