package com.pongame.classes;

import com.pongame.config.DifficultyLevel;

import java.awt.*;

public class Ball {
    private int x;
    private int y;
    private int diameter;
    private int xSpeed;
    private int ySpeed;

    public Ball(int x, int y, int diameter, int xSpeed, int ySpeed , DifficultyLevel difficulty) {
        this.x = x;
        this.y = y;
        this.diameter = diameter;
        this.xSpeed = difficulty.getSpeed();
        this.ySpeed = difficulty.getSpeed();
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(x, y, diameter, diameter);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }

    public void reverseXDirection() {
        xSpeed = -xSpeed;
    }

    public void reverseYDirection() {
        ySpeed = -ySpeed;
    }


    //getters & setters


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getDiameter() {
        return diameter;
    }

    public int getxSpeed() {
        return xSpeed;
    }

    public int getySpeed() {
        return ySpeed;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public void setxSpeed(int xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(int ySpeed) {
        this.ySpeed = ySpeed;
    }
}


