package com.pongame;

import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class GameTest {
    private Game g;

    @BeforeEach
    void setUp() {
        g = new Game(DifficultyLevel.SLOW, true, null, null);
    }

    @Test
    void testGameInitialization() {
        assertNotNull(g.getBall(), "Ball initialized");
        assertNotNull(g.getPaddle1(), "Paddle1 initialized");
        assertNotNull(g.getPaddle2(), "Paddle2 initialized");
        assertTrue(g.isSinglePlayerMode, "Game is in single player mode");
    }

    @Test
    void testPauseAndContinueGame() {
        assertFalse(g.gamePaused(), "Game not paused initially");
        g.pause_ContinueGame();
        assertTrue(g.gamePaused(), "Game not paused now");
        g.pause_ContinueGame();
        assertFalse(g.gamePaused(), "Game not continued");
    }

    @Test
    void testBallSpeedIncrease() {
        float initXSpeed = (float) g.getBall().getxSpeed();
        float initYSpeed = (float) g.getBall().getySpeed();
        g.getBall().increaseSpeed();
        assertTrue(g.getBall().getxSpeed() > initXSpeed, "Ball X speed should increase");
        assertTrue(g.getBall().getySpeed() > initYSpeed, "Ball Y speed should increase");
    }

    @Test
    void testEndGame() {
        assertTrue(g.isGameActive(), "Game not active initially");
        g.endGame();
        assertFalse(g.isGameActive(), "Game should not be active after ending");
    }

    @Test
    void testScoring() {
        int initP1Score = g.getScoreManager().getPlayer1Score();
        int initP2Score = g.getScoreManager().getPlayer2Score();
        g.getScoreManager().player1Scores();
        assertEquals(initP1Score + 1, g.getScoreManager().getPlayer1Score(), "Player 1 score not incremented");
        g.getScoreManager().player2Scores();
        assertEquals(initP2Score + 1, g.getScoreManager().getPlayer2Score(), "Player 2 score not incremented");
    }

    @Test
    void testDifficultyLevelAffectsPaddleSpeed() {
        float slowSpeed = g.getPaddle1().getSpeed();
        g = new Game(DifficultyLevel.FAST, true, null, null);
        float fastSpeed = g.getPaddle1().getSpeed();
        assertTrue(fastSpeed > slowSpeed, "Paddle speed should be higher in FAST difficulty compared to SLOW");
    }
}
