package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.graphics.HomePage;
import com.pongame.patterns.Observer;
import com.pongame.utils.Constants;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

import static com.pongame.utils.Constants.WINNING_SCORE;

public class Game {
   private static Game instance; //  holds the single instance  to imp singleton

    private Ball ball;
    private boolean gameActive = true;

    private int paddleSpeed;
    private Paddle paddle1;
    private Paddle paddle2;
    private List<Observer> observers;
    private DifficultyLevel difficultyLevel;

    private ScoreManager scoreManager;

    private HomePage mainMenu;

    private boolean gamePaused = false;
    private boolean pausedOnce = false;

    private int[] savedScores = new int[2];
    private int savedPaddleSpeed;
    private int savedBallX;
    private int savedBallY;
    private int savedPaddle1Y;
    private int savedPaddle2Y;



//    using dependency injection by passing instance difficultyLevel
    private Game(DifficultyLevel difficultyLevel) {

//        FOR SIGNLETON IMPLEMENTATION :  private constructor preventing external classes from directly creating instances of
//        the Game class.
        this.difficultyLevel = difficultyLevel;
        // Initialize game components

        this.ball = new Ball(this.difficultyLevel);
        this.paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, this.difficultyLevel);
        this.paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH,
                Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, this.difficultyLevel);



        // Initialize observers list
        this.observers = new ArrayList<>();

        this.scoreManager = ScoreManager.getInstance();
    }


    // Singleton instance creation with dependency injection

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
        if (!gamePaused) {
            this.ball.move();
            this.paddle1.followBall(this.ball);
            this.handleCollisions();
            this.notifyObservers();
        }
    }

    private void saveGameState() {
        savedScores[0] = this.scoreManager.getPlayer1Score();
        savedScores[1] = this.scoreManager.getPlayer2Score();
        savedPaddleSpeed = this.paddleSpeed;
        savedBallX = this.ball.getX();
        savedBallY = this.ball.getY();
        savedPaddle1Y = this.paddle1.getY();
        savedPaddle2Y = this.paddle2.getY();
    }

    public void pauseGame() {
        this.gamePaused = !this.gamePaused;
        if (!pausedOnce && gamePaused) {
            // Save the initial state when pausing for the first time
            pausedOnce = true;
            saveGameState();
        }
    }

    public void restartFromPause() {
        // Restore the state to the saved state
        this.scoreManager.setPlayer1Score(savedScores[0]);
        this.scoreManager.setPlayer2Score(savedScores[1]);
        this.paddleSpeed = savedPaddleSpeed;
        this.ball.setX(savedBallX);
        this.ball.setY(savedBallY);
        this.paddle1.setY(savedPaddle1Y);
        this.paddle2.setY(savedPaddle2Y);

        // Reset the pausedOnce flag
        pausedOnce = false;

        // Resume the game
        this.gamePaused = false;

        // Notify observers with the resumed game state
        this.notifyObserversWithGameState();
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
            if (this.scoreManager.getPlayer1Score() >= Constants.WINNING_SCORE || this.scoreManager.getPlayer2Score() >= WINNING_SCORE) {
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

    public void restartGame() {
        this.gameActive = true;
        this.pausedOnce = false; // Reset the flag when restarting the game
        this.scoreManager.resetScores(); // Reset scores if necessary

        // Reset ball position
        this.ball.setX(Constants.WINDOW_WIDTH / 2 - this.ball.getDiameter() / 2);
        this.ball.setY(Constants.WINDOW_HEIGHT / 2 - this.ball.getDiameter() / 2);

        // Reset paddle positions
        this.paddle1.reset();
        this.paddle2.reset();

        // Notify observers with the reset game state
        this.notifyObserversWithGameState();
    }

    public void returnToMainMenu() {
        this.gameActive = false;
        if (mainMenu != null) {
            // mainMenu.displayMainMenu();
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
