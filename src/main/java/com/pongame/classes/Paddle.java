package com.pongame.classes;

import com.pongame.config.DifficultyLevel;
import com.pongame.utils.Constants;

import java.awt.*;
import java.io.Serializable;


public class Paddle  implements Serializable {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 100;
    private int x;
    private int y;

    private int speed;

    public Paddle(int x, int y, DifficultyLevel difficultyLevel) {
        this.speed= difficultyLevel.getPaddleSpeed();
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void followBall(Ball ball) {

    }
    public void moveUp(int speed) {
        y -= this.speed;
        if (y < 0) {
            y = 0;
        }
    }

    public void moveDown(int speed) {
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
