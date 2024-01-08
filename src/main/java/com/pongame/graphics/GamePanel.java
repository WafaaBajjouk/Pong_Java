package com.pongame.graphics;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.game.Game;
import com.pongame.game.ScoreManager;
import com.pongame.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GamePanel extends JPanel implements KeyListener {
    private final Game game;
    private ScoreManager scoreManager;

    public GamePanel(Game game) {
        this.game = game;
        this.scoreManager = ScoreManager.getInstance();
        setFocusable(true);
        addKeyListener(this);

        Timer timer = new Timer(Constants.GAME_SPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.updateGame();
                repaint();
            }
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Set black background
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        if (game.isGameActive()) {
            // Draw middle line
            g.setColor(Color.WHITE);
            g.fillRect(getWidth() / 2 - Constants.LINE_WIDTH / 2, 0, Constants.LINE_WIDTH, getHeight());

            // Draw paddles
            Paddle paddle1 = game.getPaddle1();
            Paddle paddle2 = game.getPaddle2();
            g.setColor(Color.WHITE);
            g.fillRect(paddle1.getX(), paddle1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
            g.fillRect(paddle2.getX(), paddle2.getY(), Paddle.WIDTH, Paddle.HEIGHT);

            // Draw ball
            Ball ball = game.getBall();
            g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());

            // Display scores
            displayScores(g);
           // if the game is pasued show notification
             if(game.gamePaused){
                this.displayPauseNotification(g);
            }

        } else {
            // Game over, display score screen
            displayScoreScreen(g);
        }
    }

    private void displayScores(Graphics g) {
        // Display scores on the screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1: " + scoreManager.getPlayer1Score(), 50, 30);
        g.drawString("Player 2: " + scoreManager.getPlayer2Score(), getWidth() - 150, 30);
    }

    private void displayScoreScreen(Graphics g) {
        // Display scores on the score screen
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Player 1: " + scoreManager.getPlayer1Score(), getWidth() / 2 - 150, getHeight() / 2 - 50);
        g.drawString("Player 2: " + scoreManager.getPlayer2Score(), getWidth() / 2 - 150, getHeight() / 2 + 50);
        g.drawString("Game Over! Press R to restart.", getWidth() / 2 - 220, getHeight() / 2 + 150);
    }

    // Implement KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.isGameActive()) {
            if (!game.gamePaused) { // I check if the game is active and not paused before allowing paddle movements
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_UP:
                        System.out.println("UP PRESSED");
                        game.movePaddle1Up();
                        break;
                    case KeyEvent.VK_DOWN:
                        System.out.println("DOWN PRESSED");
                        game.movePaddle1Down();
                        break;
                    case KeyEvent.VK_W:
                        System.out.println("W PRESSED");
                        game.movePaddle2Up();
                        break;
                    case KeyEvent.VK_S:
                        System.out.println("S PRESSED");
                        game.movePaddle2Down();
                        break;
                }
            }

            if (e.getKeyCode() == KeyEvent.VK_P) {
                System.out.println("P PRESSED");
                if (!game.gamePaused) {
                    game.pause_ContinueGame();
                } else {
                    game.pause_ContinueGame();
                }
            }

        } else {
            // Game is not active
            switch (e.getKeyCode()) {
                case KeyEvent.VK_R:
                    System.out.println("R PRESSED");
                    game.restartGame();
                    break;
                case KeyEvent.VK_ESCAPE:
                    System.out.println("ESC PRESSED");
                    game.returnToMainMenu();
                    break;
            }
        }
    }

    private void displayPauseNotification(Graphics g) { //when the game is paused
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Paused. Press P to continue.", getWidth() / 2 - 250, getHeight() / 2);
    }


    @Override
    public void keyReleased(KeyEvent e) {
    }
}
