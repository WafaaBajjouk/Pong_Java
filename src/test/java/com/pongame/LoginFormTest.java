package com.pongame;

import com.pongame.classes.Player;
import com.pongame.dao.PlayerDAO;
import com.pongame.graphics.LoginForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class LoginFormTest {
    private LoginForm loginForm;

    @Mock
    private PlayerDAO playerDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        loginForm = new LoginForm(playerDAO);
    }

    @Test
    public void testLoginSuccess() {
        String enteredName = "TestUser";
        char[] enteredPasswordChars = "TestPassword".toCharArray();
        String enteredPassword = new String(enteredPasswordChars);
        Player mockUser = new Player();
        mockUser.setId(1);
        mockUser.setName("TestUser");
        when(playerDAO.authenticatePlayer(enteredName, enteredPassword)).thenReturn(mockUser);
        Player loggedInUser = playerDAO.authenticatePlayer(enteredName, enteredPassword);
        assertEquals(mockUser, loggedInUser);
    }

    @Test
    public void testLoginFailure() {
        String enteredName = "TestUser";
        char[] enteredPasswordChars = "WrongPassword".toCharArray();
        String enteredPassword = new String(enteredPasswordChars);
        when(playerDAO.authenticatePlayer(enteredName, enteredPassword)).thenReturn(null);
        Player loggedInUser = playerDAO.authenticatePlayer(enteredName, enteredPassword);
        assertEquals(null, loggedInUser);
    }
}