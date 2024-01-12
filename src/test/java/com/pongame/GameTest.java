package com.pongame;

import com.pongame.classes.Paddle;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
private Player player=new Player("user","2000-05-12","user");
    @BeforeEach
    public void setUp() {

        game = new Game(DifficultyLevel.SLOW, true, null);
        game = new Game(DifficultyLevel.SLOW, true, player);
    }

    @Test
    public void testGameInitializationWithNullPlayer() {
//        test checks the initialization of the Game class when the player is null
//        It ensures that the game is active, not paused, and all the game components (ball, paddles) are properly initialized.
        Game nullPlayerGame = new Game(DifficultyLevel.MEDIUM, false, null);

        assertNotNull(nullPlayerGame);
        assertTrue(nullPlayerGame.isGameActive());
        assertFalse(nullPlayerGame.gamePaused);
        assertNotNull(nullPlayerGame.getBall());
        assertNotNull(nullPlayerGame.getPaddle1());
        assertNotNull(nullPlayerGame.getPaddle2());

    }

    @Test
    public void testGameInitializationWithPlayer() {
//        checks the initialization of the Game class when a player is provided.
        assertNotNull(game);
        assertTrue(game.isGameActive());
        assertFalse(game.gamePaused);
        assertNotNull(game.getBall());
        assertNotNull(game.getPaddle1());
        assertNotNull(game.getPaddle2());

        assertNotNull(game.getPlayer());
    }


    @Test
    public void testGameInitializationWithPlayerAndAI() {
        assertNotNull(game);
        assertTrue(game.isGameActive());
        assertFalse(game.gamePaused);
        assertNotNull(game.getBall());
        assertTrue(game.isSinglePlayerMode);

        assertNotNull(game.getPaddle1());
        assertNotNull(game.getPaddle2());

        assertNotNull(game.getPlayer());
    }

    @Test
    public void testStopMoving() {
        Paddle paddle = new Paddle(0, 0, DifficultyLevel.SLOW);
        paddle.moveUp(DifficultyLevel.SLOW.getPaddleSpeed());
        assertNotEquals(0, paddle.getSpeed());
        paddle.stopMoving();
        assertEquals(0, paddle.getSpeed());
    }

}
