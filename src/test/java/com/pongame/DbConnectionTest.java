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
        // Check if the connection is not null and is open
        connection = DbConnection.getInstance();
        assertNotNull(connection, "Connection should not be null");
        try {
            assertFalse(connection.isClosed(), "Connection should be open at the beginning");
        } catch (SQLException e) {
            fail("SQLException during setup: " + e.getMessage());
        }
    }

    @Test
    public void testGetInstance() {
        // verified in setUp()
    }

    @AfterAll
    public static void tearDown() {
        // Check if the connection is closed after tearDown
        DbConnection.closeConnection();
        try {
            assertTrue(connection.isClosed(), "Connection should be closed");
        } catch (SQLException e) {
            fail("SQLException during tearDown: " + e.getMessage());
        }
    }
}
