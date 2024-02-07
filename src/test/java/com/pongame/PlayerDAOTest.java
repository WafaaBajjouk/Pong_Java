package com.pongame;

import com.pongame.classes.Player;
import com.pongame.dao.PlayerDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class PlayerDAOTest {
    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    private PlayerDAO dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dao = new PlayerDAO(conn);
    }

    @Test
    void testCreatePlayer() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        Player p = new Player("Test", "2000-01-01", "password");
        assertTrue(dao.createPlayer(p));
    }

    @Test
    void testAuthenticatePlayer_Successful() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getString("password")).thenReturn("password");
        when(rs.getInt("Id")).thenReturn(1);
        when(rs.getString("name")).thenReturn("TestPlayer");
        when(rs.getString("birthday")).thenReturn("2000-01-01");
        Player p = dao.authenticatePlayer("TestPlayer", "password");
        assertNotNull(p);
        assertEquals("TestPlayer", p.getName());
        assertEquals("2000-01-01", p.getBirthday());
        assertEquals("password", p.getPassword());
        assertEquals(1, p.getId());
    }

    @Test
    void testAuthenticatePlayer_Unsuccessful() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(false);
        Player p = dao.authenticatePlayer("NonExistentPlayer", "wrongPassword");
        assertNull(p);
    }

    @Test
    void testChangePassword_Successful() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        assertTrue(dao.changePassword(1, "newPassword"));
    }

    @Test
    void testChangePassword_Unsuccessful() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);
        assertFalse(dao.changePassword(1, "newPassword"));
    }
}
