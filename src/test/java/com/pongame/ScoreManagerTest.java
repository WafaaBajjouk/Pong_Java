package com.pongame;

import com.pongame.game.ScoreManager;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ScoreManagerTest {

    private ScoreManager scoreManager;

    @BeforeEach
    public void setUp() {
        scoreManager = ScoreManager.getInstance();
        scoreManager.resetScores();
    }

    @Test
    public void testPlayer1Scores() {
        int initialPlayer1Score = scoreManager.getPlayer1Score();
        scoreManager.player1Scores();
        assertEquals(initialPlayer1Score + 1, scoreManager.getPlayer1Score());
    }

    @Test
    public void testPlayer2Scores() {
        int initialPlayer2Score = scoreManager.getPlayer2Score();
        scoreManager.player2Scores();
        assertEquals(initialPlayer2Score + 1, scoreManager.getPlayer2Score());
    }

    @Test
    public void testResetScores() {
        scoreManager.setPlayer1Score(3);
        scoreManager.setPlayer2Score(5);
        scoreManager.resetScores();
        assertEquals(0, scoreManager.getPlayer1Score());
        assertEquals(0, scoreManager.getPlayer2Score());
    }
}