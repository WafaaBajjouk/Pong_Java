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
    public boolean isSinglePlayerMode;
    private boolean gameActive = true;
    private final List<Observer> observers = new ArrayList<>();
    private final ScoreManager scoreManager;

    public boolean gamePaused = false;
    public boolean pausedOnce = false;

    private final int[] savedScores = new int[2];
    protected Paddle paddle1;
    protected Paddle paddle2;

    // NOTEE:Constructor with dependency injection for the chosen difficulty level
    public Game(DifficultyLevel difficultyLevel, boolean isSinglePlayerMode, Player player) {
        this.player = player;
        this.isSinglePlayerMode = isSinglePlayerMode;
        this.difficultyLevel = difficultyLevel;
        this.scoreManager = ScoreManager.getInstance();
        initializeGameComponents(difficultyLevel, isSinglePlayerMode);
    }

    // Initialize game components  Ball and Paddles
    public void initializeGameComponents(DifficultyLevel difficultyLevel, boolean isSinglePlayerMode) {
        Ball cball= this.ball = new Ball(difficultyLevel,this);
        this.isSinglePlayerMode=isSinglePlayerMode;

        this.ball.setX(Constants.WINDOW_WIDTH / 2 - this.ball.getDiameter() / 2);
        this.ball.setY(Constants.WINDOW_HEIGHT / 2 - this.ball.getDiameter() / 2);

        this.paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel,this);
        this.paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH,
                Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel,this);


        collisionHandler = new CollisionHandler(this,paddle1,this.paddle2,this.ball);

//       here I do the timer for calculating the time neeeded for increasing the ball speed
        if (this.speedIncreaseTimer != null) {
            this.speedIncreaseTimer.cancel();
        }
        this.speedIncreaseTimer = new Timer();

        speedIncreaseTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cball.increaseSpeed();
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

    public void notifyObserversWithGameState() {
        for (Observer observer : this.observers) {
            observer.updateGameInfo(this.getBall(), this.getPaddle1(), this.getPaddle2());
        }
    }
        public void updateGame() {
            if (!gamePaused) {
                this.ball.move();
                this.collisionHandler.handleCollisions();
                this.notifyObserversWithGameState();
            }
        }


    private void saveGameState() {
        savedScores[0] = this.scoreManager.getPlayer1Score();
        savedScores[1] = this.scoreManager.getPlayer2Score();
        int savedPaddleSpeed = this.difficultyLevel.getPaddleSpeed();
        int savedBallX = this.ball.getX();
        int savedBallY = this.ball.getY();
        int savedPaddle1Y = this.paddle1.getY();
        int savedPaddle2Y = this.paddle2.getY();
    }



    public void endGame() {
        this.gameActive = !this.gameActive;


        if (this.speedIncreaseTimer != null) {
            this.speedIncreaseTimer.cancel();
            this.speedIncreaseTimer = null;
        }

        if (player != null) {
            System.out.println("PlayerID   "+this.player.getId());
            saveGameToDatabase();
        }
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

    public boolean gamePaused() {
        return this.gamePaused;
    }
}