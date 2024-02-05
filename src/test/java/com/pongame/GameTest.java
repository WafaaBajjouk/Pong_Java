package com.pongame;

import com.pongame.classes.Ball;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.game.PlayWithAI;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class GameTest {

    private Game game;
    private Player player = new Player("user", "2000-05-12", "user");

    @BeforeEach
    public void setUp() {
        // Initialize the game with a player
        game = new Game(DifficultyLevel.SLOW, true, player);
    }

    @Test
    public void testGameInitializationWithPlayer() {
        assertNotNull(game);
        assertTrue(game.isGameActive());
        assertFalse(game.gamePaused);
        assertNotNull(game.getBall());
        assertNotNull(game.getPaddle1());
        assertNotNull(game.getPaddle2());
        assertEquals(0, game.getScoreManager().getPlayer1Score());
        assertEquals(0, game.getScoreManager().getPlayer2Score());
        assertNotNull(game.getPlayer());
        assertEquals(player, game.getPlayer());
    }

    @Test
    public void testIncreaseSpeed() {
        Game game = new Game(DifficultyLevel.SLOW, true, new Player("user", "2000-05-12", "user"));
        double initialXSpeed = game.getBall().getxSpeed();
        double initialYSpeed = game.getBall().getySpeed();
        game.getBall().increaseSpeed();
        assertTrue(game.getBall().getxSpeed() > initialXSpeed);
        assertTrue(game.getBall().getySpeed() > initialYSpeed);
    }
    @Test
    public void testGameInitializationWithNullPlayer() {
        Game nullPlayerGame = new Game(DifficultyLevel.MEDIUM, false, null);

        assertNotNull(nullPlayerGame);
        assertTrue(nullPlayerGame.isGameActive());
        assertFalse(nullPlayerGame.gamePaused);
        assertNotNull(nullPlayerGame.getBall());
        assertNotNull(nullPlayerGame.getPaddle1());
        assertNotNull(nullPlayerGame.getPaddle2());
        assertEquals(0, nullPlayerGame.getScoreManager().getPlayer1Score());
        assertEquals(0, nullPlayerGame.getScoreManager().getPlayer2Score());
        assertNull(nullPlayerGame.getPlayer());
    }

    @Test
    public void testPause_ContinueGame() {
        assertFalse(game.gamePaused);
        game.pause_ContinueGame();
        assertTrue(game.gamePaused);
        game.pause_ContinueGame();
        assertFalse(game.gamePaused);
    }

    @Test
    public void testUpdateScoresAndResetBall() {
        game.updateScoresAndResetBall();
        assertEquals(1, game.getScoreManager().getPlayer1Score());
        assertEquals(0, game.getScoreManager().getPlayer2Score());
        game.updateScoresAndResetBall();
        assertEquals(2, game.getScoreManager().getPlayer1Score());
        assertEquals(0, game.getScoreManager().getPlayer2Score());

    }

    @Test
    public void testEndGame() {
        assertTrue(game.isGameActive());
        game.endGame();
        assertFalse(game.isGameActive());
    }



    @Test
    public void testPlayWithAIInitializationWithPlayer() {
        PlayWithAI playWithAI = new PlayWithAI(DifficultyLevel.SLOW, player);
        assertNotNull(playWithAI);
        assertTrue(playWithAI.isGameActive());
        assertFalse(playWithAI.gamePaused);
        assertNotNull(playWithAI.getBall());
        assertNotNull(playWithAI.getPaddle1());
        assertNotNull(playWithAI.getPaddle2());
        assertEquals(0, playWithAI.getScoreManager().getPlayer1Score());
        assertEquals(0, playWithAI.getScoreManager().getPlayer2Score());
        assertNotNull(playWithAI.getPlayer());
        assertEquals(player, playWithAI.getPlayer());
    }



}


