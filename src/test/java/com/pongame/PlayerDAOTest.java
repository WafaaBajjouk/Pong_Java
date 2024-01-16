package com.pongame;

import com.pongame.classes.Player;
import com.pongame.dao.PlayerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class PlayerDAOTest {

    private static Connection connection;
    private PlayerDAO playerDAO;
    private Player testPlayer;

    @BeforeAll
    public static void setUpDatabase() {
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/PongGame", "root", "wbjk1205");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        playerDAO = new PlayerDAO(connection);
        testPlayer = new Player("testuser", "2000-05-12", "password");
    }

    @Test
    public void testCreatePlayer() {
        assertTrue(playerDAO.createPlayer(testPlayer));
        assertNotNull(testPlayer.getId() > 0);
    }

    @Test
    public void testAuthenticatePlayer() {
        playerDAO.createPlayer(testPlayer);
        Player authenticatedPlayer = playerDAO.authenticatePlayer(testPlayer.getName(), testPlayer.getPassword());
        assertNotNull(authenticatedPlayer);
        assertEquals(testPlayer.getName(), authenticatedPlayer.getName());
        assertEquals(testPlayer.getBirthday(), authenticatedPlayer.getBirthday());
        assertEquals(testPlayer.getPassword(), authenticatedPlayer.getPassword());
    }

    @Test
    public void testChangePassword() {
        playerDAO.createPlayer(testPlayer);
        String newPassword = "newpassword";
        playerDAO.changePassword(testPlayer.getId(), newPassword);
        assertNotEquals(newPassword,testPlayer.getPassword());

    }


}
