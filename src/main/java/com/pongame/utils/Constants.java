package com.pongame.utils;

import com.pongame.classes.Paddle;

public interface Constants {
    public static final int WINDOW_WIDTH = 800;
    public static final int WINDOW_HEIGHT = 600;
    public static final int LINE_WIDTH = 8;
    public static final int WINNING_SCORE = 3;


    public static final int BALL_DIAMETERE=20;
    public static final int INITIAL_PADDLE_X = 0;
    public static final int INITIAL_PADDLE_Y = Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2;
    public static final int GAME_SPEED = 20;
}



