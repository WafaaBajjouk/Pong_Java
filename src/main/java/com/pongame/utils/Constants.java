package com.pongame.utils;

import com.pongame.classes.Paddle;

public interface Constants {
    int WINDOW_WIDTH = 900;
    int WINDOW_HEIGHT = 600;
    int LINE_WIDTH = 8;
    int WINNING_SCORE = 100;


    int BALL_DIAMETERE=20;
    int INITIAL_PADDLE_X = 0;
    int INITIAL_PADDLE_Y = Constants.WINDOW_HEIGHT / 2 - Paddle.HEIGHT / 2;
    int GAME_SPEED = 2;
    public static final String DB_URL = "jdbc:mysql://localhost/PongGame";
    public static final String DB_USER = "root";
    public static final String DB_PASSWORD = "wbjk1205";

}



