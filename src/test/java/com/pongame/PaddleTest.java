package com.pongame;

import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class PaddleTest {

    private Paddle paddle;
    private final DifficultyLevel difficulty = DifficultyLevel.MEDIUM;

    @BeforeEach
//    public void setUp() {
//        paddle = new Paddle(0, 0, difficulty);
//    }

    @Test
    public void testInitialization() {
        assertEquals(0, paddle.getX());
        assertEquals(0, paddle.getY());
        assertEquals(difficulty.getPaddleSpeed(), paddle.getSpeed());
        assertEquals(20, Paddle.WIDTH);
        assertEquals(100, Paddle.HEIGHT);
    }


    @Test
    public void testMoveUpAtTop() {
        paddle.setY(0);
        int speed = paddle.getSpeed();

        paddle.moveUp(speed);

        assertEquals(0, paddle.getY());
    }

    @Test
    public void testMoveDown() {
        int initialY = paddle.getY();
        int speed = paddle.getSpeed();

        paddle.moveDown(speed);

        assertEquals(initialY + speed, paddle.getY());
    }

    @Test
    public void testMoveDownAtBottom() {
        paddle.setY(Constants.WINDOW_HEIGHT - Paddle.HEIGHT);
        int speed = paddle.getSpeed();

        paddle.moveDown(speed);

        assertEquals(Constants.WINDOW_HEIGHT - Paddle.HEIGHT, paddle.getY());
    }



    @Test
    public void testGetBounds() {
        Rectangle bounds = paddle.getBounds();
        assertEquals(paddle.getX(), bounds.x);
        assertEquals(paddle.getY(), bounds.y);
        assertEquals(Paddle.WIDTH, bounds.width);
        assertEquals(Paddle.HEIGHT, bounds.height);
    }
}
