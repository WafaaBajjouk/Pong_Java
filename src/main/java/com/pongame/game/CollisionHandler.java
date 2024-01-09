package com.pongame.game;

import com.pongame.utils.Constants;

import static com.pongame.utils.Constants.*;

class CollisionHandler {
    private Game game;

    public CollisionHandler(Game game) {
        this.game = game;
    }

    public void handleCollisions() {
        if (game.isGameActive()) {
            handlePaddleCollisions();
            handleWallCollisions();
            checkForWinningCondition();
        }
    }

    private void handlePaddleCollisions() {
        if (game.getBall().getBounds().intersects(game.getPaddle1().getBounds())
                || game.getBall().getBounds().intersects(game.getPaddle2().getBounds())) {
            game.getBall().reverseXDirection();
            System.out.println("Paddle collision detected. Ball's X-direction reversed.");
        }
    }

    private void handleWallCollisions() {
        if (game.getBall().getY() <= 0 || game.getBall().getY() + game.getBall().getDiameter() >= Constants.WINDOW_HEIGHT) {
            game.getBall().reverseYDirection();
            System.out.println("Wall collision detected. Ball's Y-direction reversed.");
        }

        if (game.getBall().getX() + game.getBall().getDiameter() <= 0 || game.getBall().getX() >= Constants.WINDOW_WIDTH) {
            System.out.println("Edge collision detected. Updating scores and resetting the ball.");
            game.updateScoresAndResetBall();
        }
    }

    private void checkForWinningCondition() {
        if (ScoreManager.getInstance().getPlayer1Score() >= WINNING_SCORE || ScoreManager.getInstance().getPlayer2Score() >= WINNING_SCORE) {
            game.endGame();
        }
    }
}
