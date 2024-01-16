package com.pongame;

import com.pongame.classes.Match;
import com.pongame.config.DifficultyLevel;
import com.pongame.dao.GameDAO;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class GameDAOTest {

    private static Connection connection;
    private GameDAO gameDAO;
    private Match testMatch;

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
        gameDAO = new GameDAO(connection);
        testMatch = new Match(false, 10, 1);
    }

    @Test
    public void testSaveGame() {
        try {
            // Save the test match to the database
            gameDAO.saveGame(testMatch);
            assertTrue(testMatch.getId() > 0);
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testGetGamesByPlayerId() {
        try {
            gameDAO.saveGame(testMatch);
            List<Match> games = gameDAO.getGamesByPlayerId(testMatch.getPlayerId());
            assertNotNull(games);
            assertNotNull(games.contains(testMatch));
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }



}
