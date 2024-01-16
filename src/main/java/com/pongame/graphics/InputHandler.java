package com.pongame.graphics;

import com.pongame.game.Game;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.Serializable;

public class InputHandler implements KeyListener, Serializable {
    private final Game game;
    private final GamePanel gamePanel;

    public InputHandler(Game game, GamePanel gamePanel) {
        this.game = game;
        this.gamePanel = gamePanel;
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.isGameActive()) {
            // Check if the game is active and not paused before allowing paddle movements
            if (!game.gamePaused) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
//                        System.out.println("UP PRESSED");
                        game.getPaddle2().moveUp(this.game.getDifficultyLevel().getPaddleSpeed());
                        break;
                    case KeyEvent.VK_DOWN:
//                        System.out.println("DOWN PRESSED");
                        game.getPaddle2().moveDown(game.getDifficultyLevel().getPaddleSpeed());
                        break;
                    case KeyEvent.VK_W:
                        System.out.println("W PRESSED");

                        game.getPaddle1().moveUp(game.getDifficultyLevel().getPaddleSpeed());
                        break;
                    case KeyEvent.VK_S:
//                        System.out.println("S PRESSED");
                        game.getPaddle1().moveDown(game.getDifficultyLevel().getPaddleSpeed());
                        break;
                }
            }
            if (e.getKeyCode() == KeyEvent.VK_P) {
//                System.out.println("P PRESSED");
                game.pause_ContinueGame();
            }
            if (e.getKeyCode() == KeyEvent.VK_Q) {
//                System.out.println("Q PRESSED");
                game.endGame();
            }

        } else if (gamePanel instanceof GamePanel) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_R:
//                    System.out.println("R PRESSED");
                    gamePanel.returnToMainMenu();
                    break;

                case KeyEvent.VK_Q:
//                    System.out.println("Q PRESSED");
                    game.endGame();
                    break;
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
