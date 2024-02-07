package com.pongame;

import com.pongame.config.DifficultyLevel;
import com.pongame.game.PlayWithAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class PlayWithAITest {
    private PlayWithAI ai;

    @BeforeEach
    void setUp() {
        ai = new PlayWithAI(DifficultyLevel.MEDIUM, null, null);
    }

    @Test
    void testInitialization() {
        assertNotNull(ai.getBall(), "Ball is not  initialized");
        assertNotNull(ai.getPaddle1(), "Paddle 1 is not initialized");
        assertNotNull(ai.getPaddle2(), "AI Paddleis not initialized");
        assertTrue(ai.isSinglePlayerMode, " single player mode");
    }

    @Test
    void testAutoMoveAI() {
        int initialY = ai.getPaddle2().getY();
        ai.getBall().setY(ai.getPaddle2().getY() - 20);
        ai.autoMoveAI();
        assertTrue(ai.getPaddle2().getY() < initialY, "AI paddle should move up");

    }
}
