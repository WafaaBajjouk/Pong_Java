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

        System.out.println("from game panel :" + game.getDifficultyLevel());
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

        if (game.isGameActive()) {
            // Draw game components
            Ball ball = game.getBall();
            g.setColor(Color.RED);
            g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());

            Paddle paddle1 = game.getPaddle1();
            Paddle paddle2 = game.getPaddle2();
            g.setColor(Color.BLUE);
            g.fillRect(paddle1.getX(), paddle1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
            g.fillRect(paddle2.getX(), paddle2.getY(), Paddle.WIDTH, Paddle.HEIGHT);

            // Display scores
            displayScores(g);
        } else {
            // Game over, display score screen
            displayScoreScreen(g);
        }
    }

    private void displayScores(Graphics g) {
        // Display scores on the screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1 Score: " + scoreManager.getPlayer1Score(), 50, 50);
        g.drawString("Player 2 Score: " + scoreManager.getPlayer2Score(), 50, 80);
    }

    private void displayScoreScreen(Graphics g) {
        // Display scores on the score screen
        g.setColor(Color.BLACK);
        g.setFont(new Font("Arial", Font.BOLD, 20));
        g.drawString("Player 1 Score: " + scoreManager.getPlayer1Score(), 50, 100);
        g.drawString("Player 2 Score: " + scoreManager.getPlayer2Score(), 50, 150);
        g.drawString("Game Over! Press R to restart.", 50, 200);
    }

    // Implement KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (game.isGameActive()) {
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
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }
}
