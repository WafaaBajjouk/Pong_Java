package com.pongame.classes;

import com.pongame.utils.Constants;

import java.awt.*;

public class Paddle {
    public static final int WIDTH = 20;
    public static final int HEIGHT = 100;
    private int x;
    private int y;

    public Paddle(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, WIDTH, HEIGHT);
    }

    public void followBall(Ball ball) {

    }

    public void moveUp() {
        y -= 5; // to Adjust
        if (y < 0) {
            y = 0;
        }
    }

    public void moveDown() {
        y += 5; // to Adjust
        if (y + HEIGHT > Constants.WINDOW_HEIGHT) {
            y = Constants.WINDOW_HEIGHT - HEIGHT;
        }
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
}
