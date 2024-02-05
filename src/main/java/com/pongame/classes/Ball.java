package com.pongame.classes;

import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.utils.Constants;

import java.awt.*;
import java.io.Serializable;

public class Ball  implements Serializable {
    private int x;
    private int y;
    private int diameter;
    private static final Color BALL_COLOR = Color.RED;
    private double xSpeed;
    private double ySpeed;
    private DifficultyLevel difficulty;

    private  Game game;


    public Ball(  DifficultyLevel  difficulty, Game game) {
        this.x = Constants.WINDOW_WIDTH / 2 - this.diameter / 2;
        this.y = Constants.WINDOW_HEIGHT / 2 - this.diameter / 2;
        this.diameter = Constants.BALL_DIAMETERE;
        this.xSpeed = difficulty.getBallSpeed();
        this.ySpeed = difficulty.getBallSpeed();
        this.difficulty=difficulty;
        this.game=game;
    }
    public void move() {
        x += xSpeed;
        y += ySpeed;
    }
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

    public void draw(Graphics g) {
        g.setColor(BALL_COLOR);
        g.fillOval(x, y, diameter, diameter);
    }

    public Rectangle getBounds() {
        Rectangle r=new Rectangle(x, y, diameter, diameter);
        return r;
    }


    //    NOTE :
//    separates the speed along the x-axis (xSpeed) and y-axis (ySpeed),
//    allowing more flexibility in controlling the ball's movement.
    // Reverses the direction of the ball along the x-axis.



    public void increaseSpeed() {
        System.out.println("Time to increase ball speed");

        // Increase the speeds by 10%
        double xSpeedIncrease = Math.abs(this.getxSpeed()) * 0.1;
        double ySpeedIncrease = this.getySpeed() * 0.1;
        this.setxSpeed(this.getxSpeed() + xSpeedIncrease);
        this.setySpeed(this.getySpeed() + ySpeedIncrease);

        System.out.println("New X speed: " + this.getxSpeed());
        System.out.println("New Y speed: " + this.getySpeed());
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

    public void setDiameter(int diameter) {
        this.diameter = diameter;
    }

    public void setxSpeed(double xSpeed) {
        this.xSpeed = xSpeed;
    }

    public void setySpeed(double ySpeed) {
        this.ySpeed = ySpeed;
    }


//    handle collision


    public void handleWallCollisions() {

        if (this.getY() <= 0 || this.getY() + this.getDiameter() >= Constants.WINDOW_HEIGHT) {
            this.reverseYDirection();
            System.out.println("Wall collision detected. Ball's Y-direction reversed.");
        }

        if (this.getX() + this.getDiameter() <= 0 || this.getX() >= Constants.WINDOW_WIDTH) {
            System.out.println("Edge collision detected. Updating scores and resetting the ball.");

            if (this.getX() + this.getDiameter() <= 0) {
                this.game.getScoreManager().player2Scores();
            } else {
                this.game.getScoreManager().player1Scores();
            }

            this.reset();
        }
    }


}