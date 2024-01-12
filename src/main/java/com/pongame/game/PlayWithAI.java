package com.pongame.game;

import com.pongame.classes.Paddle;
import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;

import java.util.Random;

// Specialized version of the Game class, designed for single-player
// gameplay where the player competes against a computer-controlled paddle
public class PlayWithAI extends Game {
    private static Paddle aiPaddle ;
    private int consecutiveMisses = 0;
    private final Random random = new Random();

    public PlayWithAI(DifficultyLevel difficultyLevel, Player player) {
        super(difficultyLevel, true, player);
        // The player controls paddle1, and the computer controls paddle2
        this.aiPaddle = this.getPaddle2();
        if(this.isSinglePlayerMode) {

        }
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
    private  int iterationCount = 0;
    private  int moveIterations = 3;

     void autoMoveAI() {
        System.out.println(iterationCount);


        if (iterationCount < moveIterations) {
            iterationCount++;

            System.out.println(iterationCount);
            // Normal behavior: move towards the ball's position
            if (this.getBall().getY() < aiPaddle.getY()) {
                aiPaddle.moveUp(this.difficultyLevel.getPaddleSpeed());
            } else if (this.getBall().getY() > aiPaddle.getY() + Paddle.HEIGHT) {
                aiPaddle.moveDown(this.difficultyLevel.getPaddleSpeed());
            }
        } else {
            iterationCount++;

            // New behavior after 7 iterations: move towards the ball's position * 2
            int ballPositionY = this.getBall().getY();
            int aiPaddlePositionY = aiPaddle.getY();

            if (ballPositionY < aiPaddlePositionY) {
                aiPaddle.moveUp(this.difficultyLevel.getPaddleSpeed() * 2);
            } else if (ballPositionY > aiPaddlePositionY + Paddle.HEIGHT) {
                aiPaddle.moveDown(this.difficultyLevel.getPaddleSpeed() * 2);
            }
        }
        iterationCount++;
        // Reset iteration count after 7 iterations
        if (iterationCount > moveIterations + 7) {
            iterationCount = 0;
        }
    }


}
