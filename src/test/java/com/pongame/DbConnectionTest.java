package com.pongame;

import com.pongame.database.DbConnection;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class DbConnectionTest {

    private static Connection connection;

    @BeforeAll
    public static void setUp() {
        connection = DbConnection.getInstance();
    }

    @Test
    public void testGetInstance() {
        assertNotNull(connection);
        try {
            assertFalse(connection.isClosed());
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }

    @Test
    public void testCloseConnection() {
        DbConnection.closeConnection();
        try {
            assertTrue(connection.isClosed());
        } catch (SQLException e) {
            fail("SQLException: " + e.getMessage());
        }
    }

    @AfterAll
    public static void tearDown() {
        DbConnection.closeConnection();
    }
}
