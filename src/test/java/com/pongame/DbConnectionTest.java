package com.pongame;

import com.pongame.database.DbConnection;
import org.junit.jupiter.api.Test;
import java.sql.Connection;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DbConnectionTest {

    @Test
    public void testGetInstance() {
        Connection connection = DbConnection.getInstance();
        assertNotNull(connection, "The connection should not be null");
    }

}
