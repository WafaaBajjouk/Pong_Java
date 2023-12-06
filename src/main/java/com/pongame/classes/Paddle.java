package com.pongame.classes;

import java.awt.*;

class Paddle {
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