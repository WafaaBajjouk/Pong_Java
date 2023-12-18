package com.pongame.graphics;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.game.Game;

import javax.swing.*;
import java.awt.*;

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

