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
        // Establish a database connection before running tests
        try {
            connection = DriverManager.getConnection("jdbc:mysql://localhost/PongGame", "root", "wbjk1205");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @BeforeEach
    public void setUp() {
        // Create a new PlayerDAO instance for each test
        playerDAO = new PlayerDAO(connection);

        // Create a test Player
        testPlayer = new Player("testuser", "2000-05-12", "password");
    }

    @Test
    public void testCreatePlayer() {
        assertTrue(playerDAO.createPlayer(testPlayer));

        // Verify that the player ID is set after creation
        assertNotNull(testPlayer.getId() > 0);
    }

    @Test
    public void testAuthenticatePlayer() {
        // Create a test player and save it to the database
        playerDAO.createPlayer(testPlayer);
        Player authenticatedPlayer = playerDAO.authenticatePlayer(testPlayer.getName(), testPlayer.getPassword());
        assertNotNull(authenticatedPlayer);
        assertEquals(testPlayer.getName(), authenticatedPlayer.getName());
        assertEquals(testPlayer.getBirthday(), authenticatedPlayer.getBirthday());
        assertEquals(testPlayer.getPassword(), authenticatedPlayer.getPassword());
    }

    @Test
    public void testChangePassword() {
        // Create a test player and save it to the database
        playerDAO.createPlayer(testPlayer);
        String newPassword = "newpassword";
        playerDAO.changePassword(testPlayer.getId(), newPassword);
        assertNotEquals(newPassword,testPlayer.getPassword());

    }


}
