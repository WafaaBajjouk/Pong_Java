package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Match;
import com.pongame.classes.Paddle;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.dao.GameDAO;
import com.pongame.interfaces.IGameDAO;
import com.pongame.utils.Constants;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Timer;
import java.util.TimerTask;

import static com.pongame.database.DbConnection.connection;

public class Game implements Serializable {

    private final Player player; // The currently logged-in player
    protected Ball ball;
    DifficultyLevel difficultyLevel;
    private Timer speedIncreaseTimer;
    CollisionHandler collisionHandler;
    public boolean isSinglePlayerMode;
    public boolean gameActive = true;
    private final ScoreManager scoreManager;
    public boolean gamePaused = false;
    public boolean pausedOnce = false;
    private final int[] savedScores = new int[2];
    protected Paddle paddle1;
    protected Paddle paddle2;
    private final IGameDAO gameDAO;



    // Constructor with dependency injection for the chosen difficulty level
    public Game(DifficultyLevel difficultyLevel, boolean isSinglePlayerMode, Player player, IGameDAO gameDAO) {
        this.player = player;
        this.isSinglePlayerMode = isSinglePlayerMode;
        this.difficultyLevel = difficultyLevel;
        this.scoreManager = ScoreManager.getInstance();
        initializeGameComponents(difficultyLevel, isSinglePlayerMode);
        this.gameDAO = gameDAO;

    }

    // Initialize game components  Ball and Paddles
    public void initializeGameComponents(DifficultyLevel difficultyLevel, boolean isSinglePlayerMode) {
        this.ball = new Ball(difficultyLevel,this);
        this.isSinglePlayerMode=isSinglePlayerMode;
        this.paddle1 = new Paddle(0, Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel,this);
        this.paddle2 = new Paddle(Constants.WINDOW_WIDTH - Paddle.WIDTH,
                Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2, difficultyLevel,this);
        collisionHandler = new CollisionHandler(this,paddle1,this.paddle2,this.ball);
        //       here I do the timer for calculating the time neeeded for increasing the ball speed
        if (this.speedIncreaseTimer != null) {
            this.speedIncreaseTimer.cancel();
        }
        this.speedIncreaseTimer = new Timer();
        Ball cball=this.ball;
        speedIncreaseTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                cball.increaseSpeed();
            }
        }, 120000); // 120000 milliseconds = 2 minutes

    }

    protected void initializeGameComponents(DifficultyLevel difficultyLevel) {
    }

    public void updateGame() {
        if (!gamePaused) {
            this.ball.move();
            this.collisionHandler.handleCollisions();
        }
    }

    private void saveGameState() {
        savedScores[0] = this.scoreManager.getPlayer1Score();
        savedScores[1] = this.scoreManager.getPlayer2Score();

    }

    public void endGame() {
        this.gameActive = !this.gameActive;


        if (this.speedIncreaseTimer != null) {
            this.speedIncreaseTimer.cancel();
            this.speedIncreaseTimer = null;
        }

        if (player != null) {
            System.out.println("PlayerID to game save  "+this.player.getId());
            saveGameToDatabase(this.gameDAO);
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

    public void saveGameToDatabase(IGameDAO gameDAO) {
        if(this.player != null){
        gameDAO=new GameDAO(connection);
        try {
            int currentPlayerId = this.player.getId();
            int currentPlayerScore = this.scoreManager.getPlayer1Score();
            boolean isWinning = checkIfPlayerIsWinning(currentPlayerScore);
            Match match = new Match(this.isSinglePlayerMode, currentPlayerScore, currentPlayerId,isWinning );
            gameDAO.saveGame(match);

            System.out.println("Game Saved, Player ID: " + match.getPlayerId() + ", Winning: " + isWinning);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        }
    }

    private boolean checkIfPlayerIsWinning(int currentPlayerScore) {

        int player2Score = this.scoreManager.getPlayer2Score();
        return currentPlayerScore > player2Score;
    }

    // Initialize the game state
    public void initializeGame() {
        initializeGameComponents(difficultyLevel,this.isSinglePlayerMode);
        this.scoreManager.resetScores();
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
