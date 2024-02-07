package com.pongame;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.CollisionHandler;
import com.pongame.game.Game;
import com.pongame.game.ScoreManager;
import com.pongame.utils.Constants;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CollisionHandlerTest {
    private CollisionHandler collisionHandler;
    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game(DifficultyLevel.SLOW, true, null, null);

        Paddle paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, DifficultyLevel.SLOW, game);
        Paddle paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, DifficultyLevel.SLOW, game);
        Ball ball = new Ball(DifficultyLevel.SLOW, game);

        collisionHandler = new CollisionHandler(game, paddle1, paddle2, ball);
    }

    @Test
    void testHandleCollisions() {
        game.gameActive=true;
        collisionHandler.handleCollisions();
        assertTrue(game.isGameActive(), "Game should remain active if collision handling is successful");
    }

    @Test
    void testHandleCollisionsInactiveGame() {
        game.gameActive=false;
        collisionHandler.handleCollisions();
        assertFalse(game.isGameActive(), "Game should remain inactive if collision handling is called when game is inactive");
    }

    @Test
    void testCheckForWinningCondition() {
        ScoreManager.getInstance().setPlayer1Score(Constants.WINNING_SCORE);
        collisionHandler.handleCollisions();
        assertFalse(game.isGameActive(), "Game should end when winning condition is met");
    }
}
