package com.pongame.config;

public enum DifficultyLevel {
    SLOW(2),
    MEDIUM(5),
    FAST(8);

    private final int speed;

    DifficultyLevel(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}