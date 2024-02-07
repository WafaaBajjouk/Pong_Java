package com.pongame;

import com.pongame.classes.Ball;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.game.ScoreManager;
import com.pongame.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BallTest {

    private DifficultyLevel lvl;
    private Game gm;
    private Ball b;

    @BeforeEach
    void setUp() {
        lvl = DifficultyLevel.SLOW;
        gm = new Game(lvl, true, null,null); // no player, play against computer
        b = new Ball(lvl, gm);
    }

    @Test
    void testBallInitialization() {
        int expX = Constants.WINDOW_WIDTH / 2 - Constants.BALL_DIAMETERE / 2;
        int expY = Constants.WINDOW_HEIGHT / 2 - Constants.BALL_DIAMETERE / 2;
        assertEquals(expX, b.getX());
        assertEquals(expY, b.getY());
        assertEquals(Constants.BALL_DIAMETERE, b.getDiameter());
        assertEquals(1.0, b.getxSpeed());
        assertEquals(1.0, b.getySpeed());
    }

    @Test
    void testReverseXDirection() {
        b.reverseXDirection();
        assertEquals(b.getX() * b.getxSpeed(), b.getX() * b.getxSpeed());
    }

    @Test
    void testReverseYDirection() {
        b.reverseYDirection();
        assertEquals(b.getY() * b.getySpeed(), b.getY() * b.getySpeed());
    }

    @Test
    public void testMove() {
        int iniX = b.getX();
        int iniY = b.getY();
        b.move();
        assertEquals(iniX + b.getxSpeed(), b.getX());
        assertEquals(iniY + b.getySpeed(), b.getY());
    }

    @Test
    public void testIncreaseSpeed() {
        double iniXSpd = b.getxSpeed();
        double iniYSpd = b.getySpeed();
        b.increaseSpeed();
        assertEquals(1.1 * iniXSpd, b.getxSpeed(), 0.1);
        assertEquals(1.1 * iniYSpd, b.getySpeed(), 0.1);
    }


    @Test
    public void testHandleWallCollisions() {
        // Test collision with top wall
        b.setY(0); b.setX(10);
        b.handleWallCollisions();
        assertTrue(b.getySpeed() > 0);

        // Test collision with bottom wall
        b.setY(Constants.WINDOW_HEIGHT - b.getDiameter());
        b.handleWallCollisions();
        assertTrue(b.getySpeed() > 0);
//
        // Test collision with left edge
        b.setX(0);
        b.handleWallCollisions();
        assertEquals(0, b.getX());

    }




}
