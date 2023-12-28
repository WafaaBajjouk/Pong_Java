package com.pongame.config;

public enum DifficultyLevel {
    SLOW(3, 3),    // Ball speed, Paddle speed
    MEDIUM(4, 6),
    FAST(5, 10);

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
