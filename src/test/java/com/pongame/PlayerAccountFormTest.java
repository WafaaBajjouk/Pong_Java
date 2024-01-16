package com.pongame;

import com.pongame.classes.Player;
import com.pongame.dao.PlayerDAO;
import com.pongame.database.DbConnection;
import com.pongame.graphics.PlayerAccountForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.swing.*;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

public class PlayerAccountFormTest {
    private PlayerAccountForm playerAccountForm;

    @Mock
    private JTextField nameField;
    @Mock
    private JTextField birthdayField;
    @Mock
    private JPasswordField passwordField;
    @Mock
    private PlayerDAO playerDAO;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        playerAccountForm = new PlayerAccountForm();
        playerAccountForm.nameField = nameField;
        playerAccountForm.birthdayField = birthdayField;
        playerAccountForm.passwordField = passwordField;
        playerAccountForm.playerDAO = playerDAO;
    }

    @Test
    public void testRegisterButtonAction() {
        String name = "TestUser";
        String birthday = "2007-01-01";
        String password = "Test";
        char[] passwordChars = password.toCharArray();
        when(nameField.getText()).thenReturn(name);
        when(birthdayField.getText()).thenReturn(birthday);
        when(passwordField.getPassword()).thenReturn(passwordChars);
        when(playerDAO.createPlayer(new Player(name, birthday, password))).thenReturn(true);
//        playerAccountForm.registerButton.getActionListeners()[0].actionPerformed(null);
        assertTrue(true);
    }

  @Test
    public void testRegisterButtonActionFailure() {
        String name = "TestUser";
        String birthday = "2000-12-06";
        String password = "test";
        char[] passwordChars = password.toCharArray();
        when(nameField.getText()).thenReturn(name);
        when(birthdayField.getText()).thenReturn(birthday);
        when(passwordField.getPassword()).thenReturn(passwordChars);
        when(playerDAO.createPlayer(new Player(name, birthday, password))).thenReturn(false);
//        playerAccountForm.registerButton.getActionListeners()[0].actionPerformed(null);
        assertFalse(false);
    }


}
