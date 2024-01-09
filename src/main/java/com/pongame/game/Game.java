package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.graphics.HomePage;
import com.pongame.patterns.Observer;
import com.pongame.utils.Constants;

import java.util.ArrayList;
import java.util.List;

import static com.pongame.utils.Constants.WINNING_SCORE;

public class Game {
    private static Game instance; // Holds the single instance to implement the Singleton pattern

    private Ball ball;
    DifficultyLevel difficultyLevel;
    CollisionHandler collisionHandler;

    private boolean isSinglePlayerMode;



    private boolean gameActive = true;
    private List<Observer> observers = new ArrayList<>();
    private ScoreManager scoreManager;
    private HomePage mainMenu;

    public boolean gamePaused = false;
    private boolean pausedOnce = false;

    private int[] savedScores = new int[2];
    private int savedPaddleSpeed;
    private int savedBallX;
    private int savedBallY;
    private int savedPaddle1Y;
    private int savedPaddle2Y;
    private Paddle paddle1;
    private Paddle paddle2;

    // NOTEE:Constructor with dependency injection for the chosen difficulty level
    // Implements the Singleton pattern's private constructor to prevent direct
    // external instantiation of the Game class.
    public Game(DifficultyLevel difficultyLevel,boolean isSinglePlayerMode ) {

        this.isSinglePlayerMode = isSinglePlayerMode;


        this.difficultyLevel=difficultyLevel;
        collisionHandler = new CollisionHandler(this);
        this.scoreManager = ScoreManager.getInstance();
        initializeGameComponents(difficultyLevel);
    }

    // Initialize game components  Ball and Paddles
    private void initializeGameComponents(DifficultyLevel difficultyLevel) {
        this.ball = new Ball(difficultyLevel);
        this.ball.setX(Constants.WINDOW_WIDTH / 2 - this.ball.getDiameter() / 2);
        this.ball.setY(Constants.WINDOW_HEIGHT / 2 - this.ball.getDiameter() / 2);

        this.paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel);
        this.paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH,
                Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel);
    }

    // Singleton instance creation with dependency injection
    public static synchronized Game getInstance(DifficultyLevel difficultyLevel, boolean isSinglePlayerMode) {
        if (instance == null) {
            instance = new Game(difficultyLevel, isSinglePlayerMode);
        }
        return instance;
    }


    // Methods for adding, removing, and notifying observers
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
            if (isSinglePlayerMode) {
                autoMoveAI();
            }
            this.collisionHandler.handleCollisions();
            this.notifyObservers();
        }
    }
    private void autoMoveAI() {
        int ballCenterY = this.ball.getY() + this.ball.getDiameter() / 2;
        int aiPaddleCenterY = this.paddle2.getY() + Paddle.HEIGHT / 2;

        if (ballCenterY < aiPaddleCenterY) {
            this.paddle2.moveUp(this.difficultyLevel.getPaddleSpeed());
        } else if (ballCenterY > aiPaddleCenterY) {
            this.paddle2.moveDown(this.difficultyLevel.getPaddleSpeed());
        }
    }

    // Save the game state
    private void saveGameState() {
        savedScores[0] = this.scoreManager.getPlayer1Score();
        savedScores[1] = this.scoreManager.getPlayer2Score();
        savedPaddleSpeed = this.difficultyLevel.getPaddleSpeed();
        savedBallX = this.ball.getX();
        savedBallY = this.ball.getY();
        savedPaddle1Y = this.paddle1.getY();
        savedPaddle2Y = this.paddle2.getY();
    }

    // Pause or continue the game
    public void pause_ContinueGame() {
        this.gamePaused = !this.gamePaused;
        System.out.println("GAME PAUSED " + this.gamePaused);

        if (!pausedOnce && gamePaused) {
            pausedOnce = true;
            saveGameState();
        }
    }

    void updateScoresAndResetBall() {
        if (this.ball.getX() + this.ball.getDiameter() <= 0) {
            this.scoreManager.player2Scores();
        } else {
            this.scoreManager.player1Scores();
        }

        resetBall();
        notifyObserversWithGameState();
    }

    private void resetBall() {
        this.ball.setX(Constants.WINDOW_WIDTH / 2 - this.ball.getDiameter() / 2);
        this.ball.setY(Constants.WINDOW_HEIGHT / 2 - this.ball.getDiameter() / 2);
    }




    public void endGame() {
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
        this.initializeGame();
        gamePaused = false;
    }

    // Initialize the game state
    public void initializeGame() {
        DifficultyLevel difficultyLevelN = DifficultyLevel.NEUTRAL;
        initializeGameComponents(difficultyLevelN);
        this.scoreManager.resetScores();
        this.gameActive = true;
        this.gamePaused = false;
        this.pausedOnce = false;
        this.notifyObserversWithGameState();

        System.out.println("After initialization ");
        System.out.println("Ball position reset: X - " + ball.getX() + ", Y - " + ball.getY());
        System.out.println("Game state flags reset: gameActive - " + gameActive + ", gamePaused - " + gamePaused + ", pausedOnce - " + pausedOnce);
        System.out.println("Scores reset: Player 1 - " + scoreManager.getPlayer1Score() + ", Player 2 - " + scoreManager.getPlayer2Score());
    }


   // associating the paddle moves with the speed

    public void movePaddle1Up() {
        this.paddle1.moveUp(difficultyLevel.getPaddleSpeed());
    }

    public void movePaddle1Down() {
        this.paddle1.moveDown(difficultyLevel.getPaddleSpeed());
    }

    public void movePaddle2Up() {
        this.paddle2.moveUp(difficultyLevel.getPaddleSpeed());
    }

    public void movePaddle2Down() {
        this.paddle2.moveDown(difficultyLevel.getPaddleSpeed());
    }

    // Getters & setters
    public Ball getBall() {
        return this.ball;
    }

    public Paddle getPaddle1() {
        return this.paddle1;
    }

    public Paddle getPaddle2() {
        return this.paddle2;
    }


    public boolean isGameActive() {
        return this.gameActive;
    }

    public void setGameActive(boolean gameActive) {
        this.gameActive = gameActive;
    }

    public HomePage getMainMenu() {
        return mainMenu;
    }
    public void setSinglePlayerMode(boolean isSinglePlayerMode) {
        this.isSinglePlayerMode = isSinglePlayerMode;
    }

}
