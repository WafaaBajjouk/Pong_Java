package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.patterns.Observer;
import com.pongame.utils.Constants;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
public class Game  {
    private static Game instance;

    private Ball ball;
    private boolean gameActive = true;
    private static final int WINNING_SCORE = 3;

    private int paddleSpeed;
    private Paddle paddle1;
    private Paddle paddle2;
    private List<Observer> observers;
    private DifficultyLevel difficultyLevel;

    private ScoreManager scoreManager;


    private Game(DifficultyLevel difficultyLevel) {
        this.difficultyLevel = difficultyLevel;
        // Initialize game components
        this.ball = new Ball(Constants.WINDOW_WIDTH / 2, Constants.WINDOW_HEIGHT / 2,
                20, this.difficultyLevel);
        this.paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2,this.difficultyLevel);
        this.paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH,
                Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, this.difficultyLevel);


        System.out.println("Ball X: " + this.ball.getX() + " Y: " + this.ball.getY());
        System.out.println("Paddle 1  X: " + this.paddle1.getX() + " - Y :" + this.paddle1.getY());
        System.out.println("Paddle 2  X: " + this.paddle2.getX() + " - Y :" + this.paddle2.getY());
        // Initialize observers list
        this.observers = new ArrayList<>();

        this.scoreManager = ScoreManager.getInstance();

    }

    // Singleton design pattern: ensuring that there is only one instance of the Game class.
    // This is achieved through the private constructor and the getInstance method,
    // which creates a new instance only if one does not already exist.
    public static synchronized Game getInstance(DifficultyLevel difficultyLevel) {
        System.out.println("from game class " + difficultyLevel);
        if (instance == null) {
            instance = new Game(difficultyLevel);
        }
        return instance;
    }

    public void addObserver(Observer observer) {
        this.observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        this.observers.remove(observer);
    }

    public void registerObserver(Observer observer) {
        this.addObserver(observer);
    }

    public void notifyObservers() {
        for (Observer observer : this.observers) {
            observer.update();
        }
    }

    public void notifyObserversWithGameState() {
        for (Observer observer : this.observers) {
            observer.updateGameInfo(this.getBall(), this.getPaddle1(), this.getPaddle2());
        }
    }

    public void updateGame() {
        this.ball.move();
        this.paddle1.followBall(this.ball);
        this.handleCollisions();
        this.notifyObservers();
    }


    public void setBallPosition(int x, int y) {
        // Set the ball's position directly
        this.ball.setX(x);
        this.ball.setY(y);
    }

    private void handleCollisions() {
        // Check if the game has already ended
        if (!gameActive) {
            return;
        }

        // collision with paddle1
        if (this.ball.getBounds().intersects(this.paddle1.getBounds())) {
            this.ball.reverseXDirection();
        }
        // collision with paddle2
        if (this.ball.getBounds().intersects(this.paddle2.getBounds())) {
            this.ball.reverseXDirection();
        }
        // collision with top and bottom walls
        if (this.ball.getY() <= 0 || this.ball.getY() + this.ball.getDiameter() >= Constants.WINDOW_HEIGHT) {
            this.ball.reverseYDirection();
        }
        // if the ball goes off the left or right edge = reset the ball's position.
        if (this.ball.getX() + this.ball.getDiameter() <= 0 || this.ball.getX() >= Constants.WINDOW_WIDTH) {
            // Update scores when the ball goes off the edges
            if (this.ball.getX() + this.ball.getDiameter() <= 0) {
                this.scoreManager.player2Scores();
            } else {
                this.scoreManager.player1Scores();
            }

            // Reset the ball to the center
            this.ball.setX(Constants.WINDOW_WIDTH / 2 - this.ball.getDiameter() / 2);
            this.ball.setY(Constants.WINDOW_HEIGHT / 2 - this.ball.getDiameter() / 2);

            // Notify observers with updated scores
            this.notifyObserversWithGameState();

            // Check for a winning condition reaching 10 pts in score
            if (this.scoreManager.getPlayer1Score() >= WINNING_SCORE || this.scoreManager.getPlayer2Score() >= WINNING_SCORE) {
                this.endGame();
                this.notifyObserversWithGameState();
            }
        }
    }


    private void endGame() {
        this.gameActive = false;
        this.displayWinner();
    }

    private void displayWinner() {
        if (this.scoreManager.getPlayer1Score() >= WINNING_SCORE) {
            System.out.println("Player 1 wins!");
        } else {
            System.out.println("Player 2 wins!");
        }
    }
    public void movePaddle1Up() {
        this.paddle1.moveUp(paddleSpeed);
    }

    public void movePaddle1Down() {
        this.paddle1.moveDown(paddleSpeed);
    }

    public void movePaddle2Up() {
        this.paddle2.moveUp(paddleSpeed);
    }

    public void movePaddle2Down() {
        this.paddle2.moveDown(paddleSpeed);
    }
    // Getters for game components
    public Ball getBall() {
        return this.ball;
    }

    public Paddle getPaddle1() {
        return this.paddle1;
    }

    public Paddle getPaddle2() {
        return this.paddle2;
    }

    public DifficultyLevel getDifficultyLevel() {
        return this.difficultyLevel;
    }

    public boolean isGameActive() {
        return this.gameActive;
    }

    public void setGameActive(boolean gameActive) {
        this.gameActive = gameActive;
    }

}