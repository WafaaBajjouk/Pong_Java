package com.pongame.graphics;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.classes.Player;
import com.pongame.game.Game;
import com.pongame.game.ScoreManager;
import com.pongame.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class GamePanel extends JPanel implements KeyListener {
    private final Game game;
    private final Player Loggedplayer;
    private final ScoreManager scoreManager;
    private Image backgroundImage;

    public GamePanel(Game game, Player player) {
        this.Loggedplayer = player;
        this.game = game;
        this.game.initializeGame();

        this.scoreManager = ScoreManager.getInstance();
        setFocusable(true);
        InputHandler inputHandler = new InputHandler(game, this);
        addKeyListener(inputHandler);

        Timer timer = new Timer(Constants.GAME_SPEED, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.updateGame();
                repaint();
            }
        });
        timer.start();

        // Load background image
        try {
            backgroundImage = ImageIO.read(new File("/Users/wafaabajjouk/Desktop/Pong_Java/src/main/java/com/pongame/pictures/black.png")); // Replace with your image path
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw background image
        if (backgroundImage != null) {
            g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
        } else {
            // Fallback to black background if image fails to load
            g.setColor(Color.BLACK);
            g.fillRect(0, 0, getWidth(), getHeight());
        }

        if (game.isGameActive()) {
            // Draw middle line
            g.setColor(Color.WHITE);
            g.fillRect(getWidth() / 2 - Constants.LINE_WIDTH / 2, 0, Constants.LINE_WIDTH, getHeight());

            // Draw paddles
            Paddle paddle1 = game.getPaddle1();
            Paddle paddle2 = game.getPaddle2();
            g.setColor(Color.BLUE); // Make one paddle blue
            g.fillRect(paddle1.getX(), paddle1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
            g.setColor(Color.RED); // Make the other paddle red
            g.fillRect(paddle2.getX(), paddle2.getY(), Paddle.WIDTH, Paddle.HEIGHT);

            // Draw ball
            Ball ball = game.getBall();
            g.setColor(Color.WHITE);

            g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());

            // Display scores
            displayScores(g);

            // if the game is paused, show notification
            if (game.gamePaused) {
                this.displayPauseNotification(g);
            }
        } else {
            displayScoreScreen(g);
        }
    }

    private void displayScores(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 20));

        String player1Name = Loggedplayer != null ? Loggedplayer.getName() : "Player 1";
        g.drawString(player1Name + ": " + scoreManager.getPlayer1Score(), 50, 30);
        g.drawString("Player 2: " + scoreManager.getPlayer2Score(), getWidth() - 150, 30);
    }

    private void displayScoreScreen(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Player 1: " + scoreManager.getPlayer1Score(), getWidth() / 2 - 150, getHeight() / 2 - 50);
        g.drawString("Player 2: " + scoreManager.getPlayer2Score(), getWidth() / 2 - 150, getHeight() / 2 + 50);
        g.drawString("Game Over! Press R to restart.", getWidth() / 2 - 220, getHeight() / 2 + 150);
    }

    void returnToMainMenu() {
        game.initializeGame();
        this.setVisible(false);
        HomePage homePage = new HomePage(this.Loggedplayer);
        homePage.setVisible(true);
    }

    private void displayPauseNotification(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 30));
        g.drawString("Paused. Press P to continue.", getWidth() / 2 - 250, getHeight() / 2);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Implementation for keyPressed
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Implementation for keyTyped
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Implementation for keyReleased
    }
}
