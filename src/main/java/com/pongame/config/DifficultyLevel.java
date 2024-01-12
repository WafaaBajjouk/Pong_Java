package com.pongame.config;

public enum DifficultyLevel {
    SLOW(1, 20),
    MEDIUM(2, 10),
    FAST(3, 25);
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
