package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.game.Game;
import com.pongame.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class PongGameGraphic extends JFrame {
    private final Game game;

    public PongGameGraphic(Game game) {
        this.game = game;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Pong Game - BAJJOUK WAFAA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        GamePanel gamePanel = new GamePanel(game);
        add(gamePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = Game.getInstance();
            new PongGameGraphic(game);
        });
    }
}

class GamePanel extends JPanel {
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Draw the ball
        Ball ball = game.getBall();
        g.setColor(Color.RED);
        g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());

        // Draw the paddles
        Paddle paddle1 = game.getPaddle1();
        Paddle paddle2 = game.getPaddle2();
        g.setColor(Color.BLUE);
        g.fillRect(paddle1.getX(), paddle1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        g.fillRect(paddle2.getX(), paddle2.getY(), Paddle.WIDTH, Paddle.HEIGHT);
    }

    // BALL movements

}

