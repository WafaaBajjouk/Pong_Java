package com.pongame;

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
        Game nullPlayerGame = new Game(DifficultyLevel.MEDIUM, false, null);

        assertNotNull(nullPlayerGame);
        assertTrue(nullPlayerGame.isGameActive());
        assertFalse(nullPlayerGame.gamePaused);
        assertNotNull(nullPlayerGame.getBall());
        assertNotNull(nullPlayerGame.getPaddle1());
        assertNotNull(nullPlayerGame.getPaddle2());
        assertEquals(0, nullPlayerGame.getScoreManager().getPlayer1Score());
        assertEquals(0, nullPlayerGame.getScoreManager().getPlayer2Score());
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
    }
}
