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


class GamePanel extends JPanel {
    private final Game game;
    private ScoreManager scoreManager;

    public GamePanel(Game game) {
        this.game = game;
        this.game.initializeGame();

        this.scoreManager = ScoreManager.getInstance();
        setFocusable(true);
        InputHandler inputHandler = new InputHandler(game);
        addKeyListener(inputHandler);

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



    private void returnToMainMenu() {
        game.restartGame();

        //hides the current game window and opens the home page
        this.setVisible(false);
        HomePage homePage = new HomePage();
        homePage.setVisible(true);


    }



    private void displayPauseNotification(Graphics g) { //when the game is paused
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Paused. Press P to continue.", getWidth() / 2 - 250, getHeight() / 2);
    }


}
