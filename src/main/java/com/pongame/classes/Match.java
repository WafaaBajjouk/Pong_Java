package com.pongame.classes;

import java.util.Date;

public class Match {
    private int id;
    private Date date;
    private boolean isSinglePlayerMode;
    private int score;
    private int playerId;

    // Constructors

    public Match(  boolean isSinglePlayerMode, int score, int playerId) {

        this.isSinglePlayerMode = isSinglePlayerMode;
        this.score = score;
        this.playerId = playerId;
    }

    // Getters and setters
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



    public void setSinglePlayerMode(boolean singlePlayerMode) {
        isSinglePlayerMode = singlePlayerMode;
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
}
