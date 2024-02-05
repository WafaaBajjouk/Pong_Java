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



}
