package com.pongame;

import com.pongame.game.Game;
import com.pongame.game.PlayWithAI;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.graphics.GamePanel;
import com.pongame.graphics.InputHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.mockito.Mockito.*;

public class InputHandlerTest {

    private InputHandler inputHandler;
    private Game game;
    private GamePanel gamePanel;

    @BeforeEach
    public void setUp() {
        game = mock(Game.class);
        gamePanel = mock(GamePanel.class);
        inputHandler = new InputHandler(game, gamePanel);
    }





    @Test
    public void testKeyPressed_GameInactive() {
        when(game.isGameActive()).thenReturn(false);

        inputHandler.keyPressed(new KeyEvent(gamePanel, KeyEvent.KEY_PRESSED, System.currentTimeMillis(), 0, KeyEvent.VK_R, 'R'));

        verify(gamePanel).returnToMainMenu();
    }

    @Test
    public void testKeyReleased() {
        inputHandler.keyReleased(new KeyEvent(gamePanel, KeyEvent.KEY_RELEASED, System.currentTimeMillis(), 0, KeyEvent.VK_UP, 'W'));

    }
}
