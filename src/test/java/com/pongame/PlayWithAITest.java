package com.pongame;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.PlayWithAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class PlayWithAITest {

    private PlayWithAI playWithAI;

    @BeforeEach
    public void setUp() {
        Ball ball = mock(Ball.class);
        Paddle paddle2 = mock(Paddle.class);
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

    @Test
    public void testAutoMoveAI() {
        Player player = new Player("user", "2000-05-12", "user");
        PlayWithAI playWithAI = new PlayWithAI(DifficultyLevel.MEDIUM, player);
        //  above the AI paddle
        playWithAI.getBall().setY(playWithAI.getPaddle2().getY() - 10);
        playWithAI.updateGame();
        assertTrue(playWithAI.getPaddle2().getY() < Paddle.inity);
    }


}
