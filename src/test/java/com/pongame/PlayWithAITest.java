package com.pongame;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.PlayWithAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayWithAITest {

    private PlayWithAI playWithAI;

    @BeforeEach
    public void setUp() {
        // Create a mock ball and paddle for testing
        Ball ball = mock(Ball.class);
        Paddle paddle2 = mock(Paddle.class);

        // Set up the PlayWithAI instance with a specific difficulty level and mock objects
        playWithAI = new PlayWithAI(DifficultyLevel.SLOW, null);
        playWithAI.setBall(ball);
        playWithAI.setPaddle2(paddle2);
    }

    @Test
    public void testAutoMoveAI_BallAbovePaddle() {
        when(playWithAI.getBall().getY()).thenReturn(100);
        when(playWithAI.getPaddle2().getY()).thenReturn(150);
        playWithAI.autoMoveAI();
        assertEquals(150, playWithAI.getPaddle2().getY());
    }

    @Test
    public void testAutoMoveAI_BallBelowPaddle() {
        when(playWithAI.getBall().getY()).thenReturn(200);
        when(playWithAI.getPaddle2().getY()).thenReturn(150);
        playWithAI.autoMoveAI();
        assertEquals(150, playWithAI.getPaddle2().getY());
    }


}
