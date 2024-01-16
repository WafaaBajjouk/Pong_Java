package com.pongame;

import com.pongame.classes.Ball;
import com.pongame.config.DifficultyLevel;
import com.pongame.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

public class BallTest {

    private Ball ball;
    private final DifficultyLevel difficulty = DifficultyLevel.MEDIUM;

    @BeforeEach
    public void setUp() {
        ball = new Ball(difficulty);
    }

    @Test
    public void testInitialization() {        // Check if the ball is initialized with the correct speed
        assertEquals(difficulty.getBallSpeed(), ball.getxSpeed());
        assertEquals(difficulty.getBallSpeed(), ball.getySpeed());
    }

    @Test
    public void testMove() {
        // Check if the ball moves by comparing initial and updated positions
        int initialX = ball.getX();
        int initialY = ball.getY();
        ball.move();
        assertNotEquals(initialX, ball.getX());
        assertNotEquals(initialY, ball.getY());
    }

    @Test
    public void testGetBounds() {
        // Check if the bounds of the ball match its position and size
        Rectangle bounds = ball.getBounds();
        assertEquals(ball.getX(), bounds.x);
        assertEquals(ball.getY(), bounds.y);
        assertEquals(ball.getDiameter(), bounds.width);
        assertEquals(ball.getDiameter(), bounds.height);
    }

    @Test
    public void testReverseXDirection() {
        // Check if the X direction of the ball is reversed
        double initialXSpeed = ball.getxSpeed();
        ball.reverseXDirection();
        assertEquals(2.0, ball.getxSpeed());
    }

    @Test
    public void testReverseYDirection() {
        // Check if the Y direction of the ball is reversed
        double initialXSpeed = ball.getySpeed();
        ball.reverseYDirection();
        assertEquals(2.0, ball.getySpeed());
    }

    @Test
    public void testReset() {
        // Check if the ball is reset to its initial position and speed
        ball.setX(100);
        ball.setY(200);
        ball.setxSpeed(2.0);
        ball.setySpeed(2.0);
        ball.reset();
        assertEquals(Constants.WINDOW_WIDTH / 2 - ball.getDiameter() / 2, ball.getX());
        assertEquals(Constants.WINDOW_HEIGHT / 2 - ball.getDiameter() / 2, ball.getY());
        assertEquals(difficulty.getBallSpeed(), ball.getxSpeed());
        assertEquals(difficulty.getBallSpeed(), ball.getySpeed());
    }
}
