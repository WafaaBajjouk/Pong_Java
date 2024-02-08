package com.pongame;

import com.pongame.classes.Player;
import com.pongame.dao.PlayerDAO;
import org.h2.jdbcx.JdbcConnectionPool;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerDAOTest {

    private JdbcConnectionPool connectionPool;
    private PlayerDAO playerDAO;

    @BeforeEach
    public void setUp() throws Exception {
        connectionPool = JdbcConnectionPool.create("jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1", "user", "pass");
        try (Connection conn = connectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("CREATE TABLE IF NOT EXISTS Player (Id INT AUTO_INCREMENT PRIMARY KEY, name VARCHAR(255) NOT NULL, birthday DATE NULL, password VARCHAR(255) NOT NULL)");
        }
        playerDAO = new PlayerDAO(connectionPool.getConnection());
    }

    @AfterEach
    public void Down() throws Exception {
        try (Connection conn = connectionPool.getConnection()) {
            Statement stmt = conn.createStatement();
            stmt.execute("DROP TABLE IF EXISTS Player");
        }
        connectionPool.dispose();
    }

    @Test
    public void testCreatePlayer() throws SQLException {
        Player testPlayer = new Player("testUser", "2000-01-01", "password123");
        boolean result = playerDAO.createPlayer(testPlayer);
        assertTrue(result, "Player creation should return true");

        
    }
}
