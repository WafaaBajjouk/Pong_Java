package com.pongame;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PaddleTest {

    private Paddle paddle;

    @BeforeEach
    public void setUp() {
        paddle = new Paddle(0, 0, DifficultyLevel.SLOW, new Game(DifficultyLevel.SLOW,true,null,null));
    }

    @Test
    public void testInitialPaddlePosition() {
        // Verify that the initial X and Y
        assertEquals(0, paddle.getX());
        assertEquals(0, paddle.getY());
    }

    @Test
    public void testMoveUp() {
        // Move the paddle up
        paddle.setY(10);
        paddle.moveUp(10);
        assertEquals(0, paddle.getY());
    }

    @Test
    public void testMoveDown() {
        // Move the paddle down
        paddle.setY(10);
        paddle.moveDown(10); // Move down by 10 units
        assertEquals(20, paddle.getY());
    }



    @Test
    public void testResetPaddle() {
        paddle.moveDown(20);
        paddle.reset();
        assertEquals(0, paddle.getX());
        assertEquals(0, paddle.getY());
    }

    @Test
    public void testSetSpeed() {
        paddle.setSpeed(5);
        assertEquals(5, paddle.getSpeed());
    }

    @Test
    public void testStopMoving() {
        paddle.stopMoving();
        assertEquals(0, paddle.getSpeed());
    }
}
