package com.pongame.game;

import com.pongame.classes.Paddle;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.dao.GameDAO;
import com.pongame.interfaces.IGameDAO;
import com.pongame.database.DbConnection;
import com.pongame.interfaces.IGameDAO;

import java.util.Random;

// Specialized version of the Game class, designed for single-player
// gameplay where the player competes against a computer-controlled paddle
public class PlayWithAI extends Game {
    private static Paddle aiPaddle ;
    private final Random random = new Random();
    IGameDAO gameDAO = new GameDAO(DbConnection.getInstance());


    public PlayWithAI(DifficultyLevel difficultyLevel, Player player, IGameDAO gameDAO) {
        super(difficultyLevel, true, player, gameDAO);
    }


    @Override
    protected void initializeGameComponents(DifficultyLevel difficultyLevel) {
        super.initializeGameComponents(difficultyLevel);
        this.aiPaddle = this.getPaddle2();
        if(this.isSinglePlayerMode) {
        }    }

    @Override
    public void updateGame() {
        super.updateGame();
        if (!gamePaused && isSinglePlayerMode) {
            autoMoveAI();
        }
    }
    public void autoMoveAI() {
        int ballCenterY = this.ball.getY() + this.ball.getDiameter() / 2;
        int aiPaddleCenterY = this.paddle2.getY() + Paddle.HEIGHT / 2;

            if (ballCenterY < aiPaddleCenterY) {
                this.paddle2.moveUp(this.difficultyLevel.getPaddleSpeed());
            } else if (ballCenterY > aiPaddleCenterY) {
                this.paddle2.moveDown(this.difficultyLevel.getPaddleSpeed());
            }

    }





}
