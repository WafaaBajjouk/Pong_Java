package com.pongame.game;

import java.io.Serializable;

public class ScoreManager  implements Serializable {
    private static ScoreManager instance;

    private int player1Score;
    private int player2Score;

    private ScoreManager() {
        // Initialize scores
        this.player1Score = 0;
        this.player2Score = 0;
    }

    //hereeee SINGLETON !! Ensures that only one instance of the ScoreManager class is created.
    public static ScoreManager getInstance() {
        if (instance == null) {
            synchronized (ScoreManager.class) {
                if (instance == null) {
                    instance = new ScoreManager();
                }
            }
        }
        return instance;
    }

    public void player1Scores() {
        this.player1Score++;
    }

    public void player2Scores() {
        this.player2Score++;
    }

    public int getPlayer1Score() {
        return this.player1Score;
    }

    public int getPlayer2Score() {
        return this.player2Score;
    }

    public void resetScores() {
        this.player1Score = 0;
        this.player2Score = 0;
    }


    public void setPlayer1Score(int player1Score) {
        this.player1Score = player1Score;
    }

    public void setPlayer2Score(int player2Score) {
        this.player2Score = player2Score;
    }
}