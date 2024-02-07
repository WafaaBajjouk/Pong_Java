package com.pongame;

import com.pongame.classes.Match;
import com.pongame.dao.GameDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class GameDAOTest {
    @Mock
    private Connection conn;

    @Mock
    private PreparedStatement stmt;

    @Mock
    private ResultSet rs;

    private GameDAO dao;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        dao = new GameDAO(conn);
    }

    @Test
    void testSaveGame_Success() throws SQLException {
        Match m = new Match(true, 10, 1, true);
        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(1);
        when(stmt.getGeneratedKeys()).thenReturn(rs);
        when(rs.next()).thenReturn(true);
        when(rs.getInt(1)).thenReturn(1);

        dao.saveGame(m);

        assertEquals(1, m.getId());
    }

    @Test
    void testSaveGame_Failure() throws SQLException {
        Match m = new Match(true, 10, 1, true);
        when(conn.prepareStatement(anyString(), eq(Statement.RETURN_GENERATED_KEYS))).thenReturn(stmt);
        when(stmt.executeUpdate()).thenReturn(0);

        assertThrows(SQLException.class, () -> dao.saveGame(m));
    }

    @Test
    void testGetGamesByPlayerId() throws SQLException {
        when(conn.prepareStatement(anyString())).thenReturn(stmt);
        when(stmt.executeQuery()).thenReturn(rs);
        when(rs.next()).thenReturn(true).thenReturn(false);
        when(rs.getInt("Id")).thenReturn(1);
        when(rs.getDate("date_of_game")).thenReturn(null);
        when(rs.getInt("score")).thenReturn(10);
        when(rs.getBoolean("isSinglePlayerMode")).thenReturn(true);
        when(rs.getBoolean("win")).thenReturn(true);

        List<Match> games = dao.getGamesByPlayerId(1);

        assertNotNull(games);
        assertEquals(1, games.size());
        Match m = games.get(0);
        assertEquals(1, m.getId());
        assertNull(m.getDate());
        assertEquals(10, m.getScore());
        assertTrue(m.isSinglePlayerMode());
        assertTrue(m.isWin());
    }



}
