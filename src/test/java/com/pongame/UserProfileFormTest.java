package com.pongame;

import com.pongame.classes.Player;
import com.pongame.graphics.UserProfileForm;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class UserProfileFormTest {
    private Player user;

    @BeforeEach
    public void setUp() {
        user = new Player("TestUser","2009-11-01","pass");

    }

    @Test
    public void userProfileFormInitialization() {
        UserProfileForm userProfileForm = new UserProfileForm(user);
        assertNotNull(userProfileForm);
        assertEquals("User Profile", userProfileForm.getTitle());
        assertNotNull(userProfileForm.nameTextField);
        assertEquals("TestUser", userProfileForm.nameTextField.getText());
        assertNotNull(userProfileForm.birthdayTextField);
        assertEquals("2009-11-01", userProfileForm.birthdayTextField.getText());
        assertNotNull(userProfileForm.passwordField);
    }


}