package com.pongame.classes;

import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.utils.Constants;

import java.awt.*;
import java.io.Serializable;

public class Ball  implements Serializable {
    private int x;
    private int y;
    private static final Color BALL_COLOR = Color.RED;
    private double xSpeed;
    private double ySpeed;
    private DifficultyLevel difficulty;
    private int diametre;
    private  Game game;



    public Ball(DifficultyLevel  difficulty, Game game) {
        System.out.println("ball created ");
        this.x = Constants.WINDOW_WIDTH / 2 - Constants.BALL_DIAMETERE / 2;
        this.y = Constants.WINDOW_HEIGHT / 2 - Constants.BALL_DIAMETERE / 2;
        this.xSpeed = difficulty.getBallSpeed();
        this.ySpeed = difficulty.getBallSpeed();
        this.difficulty=difficulty;
        this.game=game;
        this.diametre=Constants.BALL_DIAMETERE;
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
        this.x = Constants.WINDOW_WIDTH / 2 - Constants.BALL_DIAMETERE / 2;
        this.y = Constants.WINDOW_HEIGHT / 2 - Constants.BALL_DIAMETERE / 2;
        this.xSpeed = difficulty.getBallSpeed();
        this.ySpeed = difficulty.getBallSpeed();
    }

    public void draw(Graphics g) {
        g.setColor(BALL_COLOR);
        g.fillOval(x, y, Constants.BALL_DIAMETERE, Constants.BALL_DIAMETERE);
    }

    public Rectangle getBounds() {
        Rectangle r=new Rectangle(x, y, Constants.BALL_DIAMETERE, Constants.BALL_DIAMETERE);
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


//    handle collision


    public void handleWallCollisions() {

        if (this.getY() <= 0 || this.getY() + Constants.BALL_DIAMETERE >= Constants.WINDOW_HEIGHT) {
            this.reverseYDirection();
            System.out.println("Wall collision detected. Ball's Y-direction reversed.");
        }

        if (this.getX() + Constants.BALL_DIAMETERE <= 0 || this.getX() >= Constants.WINDOW_WIDTH) {
            System.out.println("Edge collision detected. Updating scores and resetting the ball.");

            if (this.getX() + Constants.BALL_DIAMETERE <= 0) {
                this.game.getScoreManager().player2Scores();
            } else {
                this.game.getScoreManager().player1Scores();
            }

            this.reset();
        }
    }




}