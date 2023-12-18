package com.pongame.graphics;


import com.pongame.game.Game;
import com.pongame.utils.Constants;

import javax.swing.*;
import java.awt.*;

public class PongGameGraphic extends JFrame {
    private final Game game;

    public PongGameGraphic(Game game) {
        this.game = game;
        initializeUI();
    }

    private void initializeUI() {
        setTitle("Pong Game - BAJJOUK WAFAA");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Constants.WINDOW_WIDTH, Constants.WINDOW_HEIGHT);
        setLocationRelativeTo(null);
        GamePanel gamePanel = new GamePanel(game);
        add(gamePanel);
        setVisible(true);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Game game = Game.getInstance();
            new PongGameGraphic(game);
        });
    }
}

