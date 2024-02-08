package com.pongame.classes;

import java.util.Date;

public class Match {
    private int id;
    private Date date;
    private boolean isSinglePlayerMode;
    private int score;
    private int playerId;
    private boolean win;

    public Match(boolean isSinglePlayerMode, int score, int playerId, boolean win) {
        this.isSinglePlayerMode = isSinglePlayerMode;
        this.score = score;
        this.playerId = playerId;
        this.win = win;
        this.date = new Date();
    }

    // Getters and Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isSinglePlayerMode() {
        return isSinglePlayerMode;
    }

    public void setSinglePlayerMode(boolean isSinglePlayerMode) {
        this.isSinglePlayerMode = isSinglePlayerMode;
    }

    public int getPlayerId() {
        return playerId;
    }

    public void setPlayerId(int playerId) {
        this.playerId = playerId;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isWin() {
        return win;
    }

    public void setWin(boolean win) {
        this.win = win;
    }
}
