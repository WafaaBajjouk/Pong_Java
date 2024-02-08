package com.pongame.classes;

import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.utils.Constants;

import java.awt.*;
import java.io.Serializable;


public class Paddle  implements Serializable {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 100;
    private int x;
    public   int initx;
    public static int inity;
    private int y;
    private int speed;

    private final Game game;

    public Paddle(int x, int y, DifficultyLevel difficultyLevel, Game game) {
        this.speed= difficultyLevel.getPaddleSpeed();
        this.x = x;
        this.y = y;
        this.initx=x;
        this.inity=y;
        this.game = game;
    }

    public void moveUp(int speed) {

        y -= this.speed;
        if (y < 0) {
            y = 0;
        }
    }

    public void moveDown(int speed) {//        y coordinate increases as you move down.bc , TOP left point is (0,0)
        y += speed;
        if (y + HEIGHT > Constants.WINDOW_HEIGHT) {
            y = Constants.WINDOW_HEIGHT - HEIGHT;
        }
    }

    public void reset() {        // Reset the paddle to its initial position
        this.x = Constants.INITIAL_PADDLE_X;
        this.y = Constants.INITIAL_PADDLE_Y;
    }

    public Rectangle getBounds() {

        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    public void handlePaddleCollisions() {
        if (game.getBall().getBounds().intersects(this.getBounds())
                || game.getBall().getBounds().intersects(this.getBounds())) {
            game.getBall().reverseXDirection();
            System.out.println("Paddle collision detected. Ball's X-direction reversed.");
        }
    }

    // Getters and setters
    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return this.speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public void stopMoving() {
        this.speed = 0;
    }
}
