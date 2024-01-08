package com.pongame.config;

public enum DifficultyLevel {
    SLOW(3, 6),
    MEDIUM(4, 8),
    FAST(5, 10),
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
