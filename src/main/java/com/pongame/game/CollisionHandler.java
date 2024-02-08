package com.pongame.game;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.utils.Constants;

import java.io.Serializable;

import static com.pongame.utils.Constants.*;


public class CollisionHandler  implements Serializable {
    private final Game game;
    private Paddle paddle1;
    private Paddle paddle2;
    private Ball ball;


    public CollisionHandler(Game game, Paddle p1,Paddle p2, Ball ball) {
        this.game = game;
        this.paddle1=p1;
        this.paddle2=p2;
        this.ball=ball;
    }

    public void handleCollisions() {
        if (game.isGameActive()) {
            this.paddle1.handlePaddleCollisions();
            this.paddle2.handlePaddleCollisions();
            this.ball.handleWallCollisions();
            checkForWinningCondition();
        }
    }

    private void checkForWinningCondition() {
        if (ScoreManager.getInstance().getPlayer1Score() >= WINNING_SCORE || ScoreManager.getInstance().getPlayer2Score() >= WINNING_SCORE) {
            game.endGame();
        }
    }
}
