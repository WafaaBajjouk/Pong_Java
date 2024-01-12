package com.pongame.classes;

import com.pongame.config.DifficultyLevel;
import com.pongame.utils.Constants;

import java.awt.*;
import java.io.Serializable;

public class Ball  implements Serializable {
    private int x;
    private int y;
    private int diameter;
    private static final Color BALL_COLOR = Color.RED;

    private int xSpeed;
    private int ySpeed;

    private DifficultyLevel difficulty;

    public Ball(  DifficultyLevel  difficulty) {
//        INITIALE STATE OF THE BALL IS IN THE CENTRE
        this.x = Constants.WINDOW_WIDTH / 2 - this.diameter / 2;
        this.y = Constants.WINDOW_HEIGHT / 2 - this.diameter / 2;
        this.diameter = Constants.BALL_DIAMETERE;
        this.xSpeed = difficulty.getBallSpeed();
        this.ySpeed = difficulty.getBallSpeed();
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    public void draw(Graphics g) {
        g.setColor(BALL_COLOR);
        g.fillOval(x, y, diameter, diameter);
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, diameter, diameter);
    }


    //    NOTE :
//    separates the speed along the x-axis (xSpeed) and y-axis (ySpeed),
//    allowing more flexibility in controlling the ball's movement.
    // Reverses the direction of the ball along the x-axis.
    public void reverseXDirection() {
        xSpeed = -xSpeed;
    }

    // Reverses the direction of the ball along the y-axis.
    public void reverseYDirection() {
        ySpeed = -ySpeed;
    }

    public void reset() {
        // Reset the ball to the center
        this.x = Constants.WINDOW_WIDTH / 2 - this.diameter / 2;
        this.y = Constants.WINDOW_HEIGHT / 2 - this.diameter / 2;
        this.xSpeed = difficulty.getBallSpeed();
        this.ySpeed = difficulty.getBallSpeed();
    }


    // Getters & setters...

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