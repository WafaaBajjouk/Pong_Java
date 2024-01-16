package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Match;
import com.pongame.classes.Paddle;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.dao.GameDAO;
import com.pongame.database.DbConnection;
import com.pongame.patterns.Observer;
import com.pongame.utils.Constants;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Game implements Serializable {
    private final Player player; // The currently logged-in player

    protected Ball ball;
    DifficultyLevel difficultyLevel;
    private Timer speedIncreaseTimer;

    CollisionHandler collisionHandler;

    private long startTime;


    public boolean isSinglePlayerMode;



    private boolean gameActive = true;
    private final List<Observer> observers = new ArrayList<>();
    private final ScoreManager scoreManager;


    public boolean gamePaused = false;
    public boolean pausedOnce = false;

    private final int[] savedScores = new int[2];
    private int savedPaddleSpeed;
    private int savedBallX;
    private int savedBallY;
    private int savedPaddle1Y;
    private int savedPaddle2Y;
    protected Paddle paddle1;
    protected Paddle paddle2;

    // NOTEE:Constructor with dependency injection for the chosen difficulty level
    // Implements the Singleton pattern's private constructor to prevent direct
    // external instantiation of the Game class.
    public Game(DifficultyLevel difficultyLevel,boolean isSinglePlayerMode ,Player player) {


        this.player = player;

        this.isSinglePlayerMode = isSinglePlayerMode;

        this.difficultyLevel=difficultyLevel;
        collisionHandler = new CollisionHandler(this);
        this.scoreManager = ScoreManager.getInstance();
        initializeGameComponents(difficultyLevel,isSinglePlayerMode);



    }


    // Initialize game components  Ball and Paddles
    public void initializeGameComponents(DifficultyLevel difficultyLevel, boolean isSinglePlayerMode) {
        this.ball = new Ball(difficultyLevel);
        this.isSinglePlayerMode=isSinglePlayerMode;

        this.ball.setX(Constants.WINDOW_WIDTH / 2 - this.ball.getDiameter() / 2);
        this.ball.setY(Constants.WINDOW_HEIGHT / 2 - this.ball.getDiameter() / 2);

        this.paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel);
        this.paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH,
                Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel);

//       here I do the timer for calculating the time neeeded for increasing the ball speed
        if (this.speedIncreaseTimer != null) {
            this.speedIncreaseTimer.cancel();
        }
        this.speedIncreaseTimer = new Timer();
        speedIncreaseTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                increaseSpeed();
            }
        }, 120000); // 120000 milliseconds = 2 minutes

        System.out.println("BALL SPEE  X:"+this.getBall().getxSpeed()+"Y :"+this.getBall().getySpeed());

    }
//
//    // Singleton instance creation with dependency injection
//    public static synchronized Game getInstance(DifficultyLevel difficultyLevel, boolean isSinglePlayerMode) {
//        System.out.println("SINLE MODE :"+isSinglePlayerMode);
//
//        if (instance == null) {
//            instance = new Game(difficultyLevel, isSinglePlayerMode);
//        }
//        return instance;
//    }


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
                this.collisionHandler.handleCollisions();
                this.notifyObservers();
            }
        }


    public void increaseSpeed() {
        System.out.println("Time to increase ball speed");

        // Increase the speeds by 10%
        double xSpeedIncrease = Math.abs(this.getBall().getxSpeed()) * 0.1;
        double ySpeedIncrease = this.getBall().getySpeed() * 0.1;
        this.getBall().setxSpeed(this.getBall().getxSpeed() + xSpeedIncrease);
        this.getBall().setySpeed(this.getBall().getySpeed() + ySpeedIncrease);

        System.out.println("New X speed: " + this.getBall().getxSpeed());
        System.out.println("New Y speed: " + this.getBall().getySpeed());
    }




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
    public boolean pause_ContinueGame() {
        this.gamePaused = !this.gamePaused;
        System.out.println("GAME PAUSED " + this.gamePaused);

        if (!pausedOnce && gamePaused) {
            pausedOnce = true;
            saveGameState();

        }
        return  this.gamePaused;
    }

    public void updateScoresAndResetBall() {
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

        if (this.speedIncreaseTimer != null) {
            this.speedIncreaseTimer.cancel();
            this.speedIncreaseTimer = null;
        }

        if (player != null) {
            System.out.println("PlayerID   "+this.player.getId());
            saveGameToDatabase();
        }
    }

    private void saveGameToDatabase() {
        Connection connection = DbConnection.getInstance();
        try {
            Match match = new Match(this.isSinglePlayerMode, this.scoreManager.getPlayer1Score(), this.player.getId());
            GameDAO gameDAO = new GameDAO(connection);
            System.out.println("Game Saved player Id  "+match.getPlayerId());
            gameDAO.saveGame(match);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }



    // Initialize the game state
    public void initializeGame() {

        initializeGameComponents(difficultyLevel,this.isSinglePlayerMode);

        this.scoreManager.resetScores();
        this.gameActive = true;
        this.gamePaused = false;
        this.pausedOnce = false;
        this.notifyObserversWithGameState();

        System.out.println("After initialization ");
        System.out.println("SINGLE MODE :"+this.isSinglePlayerMode);

        System.out.println("Ball position reset: X - " + ball.getX() + ", Y - " + ball.getY());
        System.out.println("Game state flags reset: gameActive - " + gameActive + ", gamePaused - " + gamePaused + ", pausedOnce - " + pausedOnce);
        System.out.println("Scores reset: Player 1 - " + scoreManager.getPlayer1Score() + ", Player 2 - " + scoreManager.getPlayer2Score());
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

    public DifficultyLevel getDifficultyLevel() {
        return this.difficultyLevel;
    }

    public ScoreManager getScoreManager() {
        return this.scoreManager;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void setBall(Ball ball) {
        this.ball = ball;
    }

    public void setPaddle1(Paddle paddle1) {
        this.paddle1 = paddle1;
    }

    public void setPaddle2(Paddle paddle2) {
        this.paddle2 = paddle2;
    }
}