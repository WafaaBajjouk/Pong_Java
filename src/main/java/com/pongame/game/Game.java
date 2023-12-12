package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.patterns.Observer;
import com.pongame.utils.Constants;

import java.util.ArrayList;
import java.util.List;

public class Game {
    private static Game instance;

    private Ball ball;
    private Paddle paddle1;
    private Paddle paddle2;
    private List<Observer> observers;

    private Game() {
        // Initialize game components
        ball = new Ball(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2, 20, DifficultyLevel.MEDIUM);
        paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2);
        paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2);

        // Initialize observers list
        observers = new ArrayList<>();
    }

    public static Game getInstance() {
        if (instance == null) {
            instance = new Game();
        }
        return instance;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void registerObserver(Observer observer) {
        addObserver(observer);

    }

    public void notifyObservers() {
        for (Observer observer : observers) {
            observer.update();
        }
    }

    public void notifyObserversWithGameState() {
        for (Observer observer : observers) {
            observer.updateGameInfo(this.getBall(), this.getPaddle1(), this.getPaddle2());
        }
    }

    public void updateGame() {
        ball.move();
        paddle1.followBall(ball);
        handleCollisions();
        notifyObservers();
    }

    public void setBallPosition(int x, int y) {
        // Set the ball's position directly
        ball.setX(x);
        ball.setY(y);
    }

    private void handleCollisions() {
        // collision with paddle1
        if (ball.getBounds().intersects(paddle1.getBounds())) {
            ball.reverseXDirection();
        }
        //  collision with paddle2
        if (ball.getBounds().intersects(paddle2.getBounds())) {
            ball.reverseXDirection();
        }
        // collision with top and bottom walls
        if (ball.getY() <= 0 || ball.getY() + ball.getDiameter() >= Constants.WINDOW_HEIGHT) {
            ball.reverseYDirection();
        }
        //  if the ball goes off the left or right edge= reset the ball's position.
        if (ball.getX() + ball.getDiameter() <= 0 || ball.getX() >= Constants.WINDOW_WIDTH) {
            // Reset the ball to the center
            ball.setX(Constants.WINDOW_WIDTH / 2 - ball.getDiameter() / 2);
            ball.setY(Constants.WINDOW_HEIGHT / 2 - ball.getDiameter() / 2);
        }
    }

    public void movePaddle1Up() {
        paddle1.moveUp();
    }

    public void movePaddle1Down() {
        paddle1.moveDown();
    }

    public void movePaddle2Up() {
        paddle2.moveUp();
    }

    public void movePaddle2Down() {
        paddle2.moveDown();
    }

    // Getters for game components
    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle1() {
        return paddle1;
    }

    public Paddle getPaddle2() {
        return paddle2;
    }
}
