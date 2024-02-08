package com.pongame.classes;

import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.utils.Constants;

import java.awt.*;
import java.io.Serializable;

public class Ball implements Serializable {
    private int x;
    private int y;
    private static final Color BALL_COLOR = Color.RED;
    private double xSpeed;
    private double ySpeed;
    private DifficultyLevel difficulty;
    private int diametre;
    private Game game;

    public Ball(DifficultyLevel difficulty, Game game) {
        System.out.println("ball created ");
        this.x = Constants.WINDOW_WIDTH / 2 - this.diametre / 2;
        this.y = Constants.WINDOW_HEIGHT / 2 - this.diametre / 2;
        this.xSpeed = difficulty.getBallSpeed();
        this.ySpeed = difficulty.getBallSpeed();
        this.difficulty = difficulty;
        this.game = game;
        this.diametre = Constants.BALL_DIAMETERE;
    }

    public void move() {
        x += xSpeed;
        y += ySpeed;
    }

    public boolean reverseXDirection() {
        try {
            xSpeed = -xSpeed;
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void reverseYDirection() {
        ySpeed = -ySpeed;
    }

    public void reset() {
        this.x = Constants.WINDOW_WIDTH / 2 - this.diametre / 2; // Use local variable
        this.y = Constants.WINDOW_HEIGHT / 2 - this.diametre / 2; // Use local variable
        this.xSpeed = difficulty.getBallSpeed();
        this.ySpeed = difficulty.getBallSpeed();
    }

    public void draw(Graphics g) {
        g.setColor(BALL_COLOR);
        g.fillOval(x, y, this.diametre, this.diametre); // Use local variable
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, this.diametre, this.diametre); // Use local variable
    }

    public void increaseSpeed() {
        System.out.println("Time to increase ball speed");
        // Increase the speeds by 10%
        double xSpeedIncrease = Math.abs(this.xSpeed) * 0.1;
        double ySpeedIncrease = Math.abs(this.ySpeed) * 0.1;
        this.xSpeed += xSpeedIncrease;
        this.ySpeed += ySpeedIncrease;

        System.out.println("New X speed: " + this.xSpeed);
        System.out.println("New Y speed: " + this.ySpeed);
    }

    public void handleWallCollisions() {
        if (this.y <= 0 || this.y + this.diametre >= Constants.WINDOW_HEIGHT) { // Use local variable
            this.reverseYDirection();
            System.out.println("Wall collision detected. Ball's Y-direction reversed.");
        }

        if (this.x + this.diametre <= 0 || this.x >= Constants.WINDOW_WIDTH) { // Use local variable
            System.out.println("Edge collision detected. Updating scores and resetting the ball.");
            if (this.x + this.diametre <= 0) { // Use local variable
                this.game.getScoreManager().player2Scores();
            } else {
                this.game.getScoreManager().player1Scores();
            }
            this.reset();
        }
    }

    // Getters & Setters...

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double getxSpeed() {
        return Math.abs(xSpeed);
    }

    public double getySpeed() {
        return Math.abs(ySpeed);
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getDiameter() {
        return diametre;
    }

    public void setDiametre(int diametre) {
        this.diametre = diametre;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }
}
