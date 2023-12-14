package com.pongame.game;

public class ScoreManager {
    private static ScoreManager instance;

    private int player1Score;
    private int player2Score;

    private ScoreManager() {
        // Initialize scores
        player1Score = 0;
        player2Score = 0;
    }


    public static ScoreManager getInstance() { //SINGLETON !!ensures that only one instance of the ScoreManager class is created.
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
        player1Score++;
    }

    public void player2Scores() {
        player2Score++;
    }

    public int getPlayer1Score() {
        return player1Score;
    }

    public int getPlayer2Score() {
        return player2Score;
    }

    public void resetScores() {
        player1Score = 0;
        player2Score = 0;
    }
}
