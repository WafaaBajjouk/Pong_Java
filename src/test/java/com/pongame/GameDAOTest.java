package com.pongame;

import com.pongame.classes.Match;
import com.pongame.dao.GameDAO;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameDAOTest {

    private JdbcConnectionPool connectionPool;
    private GameDAO gameDAO;

    @BeforeEach
    public void setUp() throws Exception {
        connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "user", "pass");

        try (Connection conn = connectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            // Recreate tables before each test
            stmt.execute("CREATE TABLE IF NOT EXISTS Player (Id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, birthday DATE NULL, password VARCHAR(255) NULL)");
            stmt.execute("CREATE TABLE IF NOT EXISTS Game (Id INT AUTO_INCREMENT PRIMARY KEY, date_of_game DATE NOT NULL, score INT NULL, isSinglePlayerMode TINYINT(1), win TINYINT(1) NULL, player_id INT, FOREIGN KEY (player_id) REFERENCES Player(Id))");
            stmt.execute("INSERT INTO Player (name, birthday, password) VALUES ('user', '2000-01-15', 'user')");
        }

        gameDAO = new GameDAO(connectionPool.getConnection());
    }

    @AfterEach
    public void tearDown() throws Exception {
        try (Connection conn = connectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            // Clean the database after each test
            stmt.execute("DROP TABLE IF EXISTS Game");
            stmt.execute("DROP TABLE IF EXISTS Player");
        }
        connectionPool.dispose();
    }

    @Test
    public void testSaveGame() throws SQLException {
        int playerId = 1;
        Match newGame = new Match(true, 100, playerId, true);
        gameDAO.saveGame(newGame);

        // Verify the game was saved correctly
        List<Match> games = gameDAO.getGamesByPlayerId(playerId);
        assertFalse(games.isEmpty(), "The game list should not be empty");
        Match savedGame = games.get(0);
        assertTrue(savedGame.isWin(), "The game should be marked as won");
        assertTrue(savedGame.isSinglePlayerMode(), "The game should be in single-player mode");
    }
}
