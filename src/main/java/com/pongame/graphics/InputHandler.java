package com.pongame.graphics;

import com.pongame.game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {
    private final Game game;

    public InputHandler(Game game) {
        this.game = game;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.isGameActive()) {
            if (!game.gamePaused) {// Check if the game is active and not paused before allowing paddle movements
                handleGameInput(e);
            }
            // ACTIVE BUT NOT PAUSED

            if (e.getKeyCode() == KeyEvent.VK_P) {
                game.pause_ContinueGame();
            }

            if (e.getKeyCode() == KeyEvent.VK_Q) {
                game.endGame();
            }
        } else {
            handleGameOverInput(e);
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    private void handleGameInput(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP:
                game.movePaddle1Up();
                break;
            case KeyEvent.VK_DOWN:
                game.movePaddle1Down();
                break;
            case KeyEvent.VK_W:
                game.movePaddle2Up();
                break;
            case KeyEvent.VK_S:
                game.movePaddle2Down();
                break;
        }
    }

    private void handleGameOverInput(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_R:
                game.restartGame();
                break;
            case KeyEvent.VK_Q:
                game.endGame();
                break;
        }
    }
}
