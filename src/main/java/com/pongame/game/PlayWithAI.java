package com.pongame.game;

import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
//specialized version of the Game class, designed for single-player
// gameplay where the player competes against a computer-controlled paddle
public class PlayWithAI extends Game {
    private Paddle aiPaddle;

    public PlayWithAI(DifficultyLevel difficultyLevel) {
        super(difficultyLevel,true);
        //  the player controls paddle1, and the computer controls paddle2
        this.aiPaddle = this.getPaddle2();
    }

    @Override
    public void updateGame() {
        if (!gamePaused) {
            this.getBall().move();
            this.getPaddle1().followBall(this.getBall());
            this.autoMoveAI();
            this.collisionHandler.handleCollisions();
            this.notifyObservers();
        }
    }

    // AI movement logic, specific to PlayWithAI
    private void autoMoveAI() {
        if (this.getBall().getY() < aiPaddle.getY()) {
            aiPaddle.moveUp(this.difficultyLevel.getPaddleSpeed());
        } else if (this.getBall().getY() > aiPaddle.getY() + Paddle.HEIGHT) {
            aiPaddle.moveDown(this.difficultyLevel.getPaddleSpeed());
        }
    }
}
