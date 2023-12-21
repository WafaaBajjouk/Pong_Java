package com.pongame.graphics;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.game.Game;
import com.pongame.utils.Constants;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

class GamePanel extends JPanel implements KeyListener {
    private final Game game;

    public GamePanel(Game game) {
        this.game = game;

        System.out.println("from game panel :"+game.getDifficultyLevel());
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

        Ball ball = game.getBall();

//        System.out.println("from game panel ");

        g.setColor(Color.RED);
        g.fillOval(ball.getX(), ball.getY(), ball.getDiameter(), ball.getDiameter());
        Paddle paddle1 = game.getPaddle1();
        Paddle paddle2 = game.getPaddle2();
        g.setColor(Color.BLUE);
        g.fillRect(paddle1.getX(), paddle1.getY(), Paddle.WIDTH, Paddle.HEIGHT);
        g.fillRect(paddle2.getX(), paddle2.getY(), Paddle.WIDTH, Paddle.HEIGHT);
    }

    // Implement KeyListener methods
    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
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

    @Override
    public void keyReleased(KeyEvent e) {
    }
}