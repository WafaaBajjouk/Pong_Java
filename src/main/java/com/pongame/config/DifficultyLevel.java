package com.pongame.config;

public enum DifficultyLevel {
    SLOW(3, 10),
    MEDIUM(4, 15),
    FAST(5, 20),
    NEUTRAL(1, 1);

    private final int ballSpeed;
    private final int paddleSpeed;

    DifficultyLevel(int ballSpeed, int paddleSpeed) {
        this.ballSpeed = ballSpeed;
        this.paddleSpeed = paddleSpeed;
    }

    public int getBallSpeed() {
        return this.ballSpeed;
    }

    public int getPaddleSpeed() {
        return this.paddleSpeed;
    }
}
