package com.pongame.patterns;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;

public interface Observer {
    void update();
    void updateGameInfo(Ball ball, Paddle paddle1, Paddle paddle2);
    void returnToMainMenu();


}