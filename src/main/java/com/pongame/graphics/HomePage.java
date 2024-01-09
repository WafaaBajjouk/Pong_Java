package com.pongame.graphics;

import com.pongame.classes.Ball;
import com.pongame.classes.Paddle;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.patterns.Observer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private JComboBox<DifficultyLevel> difficultyComboBox;
    private JButton startSinglePlayerButton;

    public HomePage() {
        setTitle("Pong Game Home Page");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyComboBox = new JComboBox<>(DifficultyLevel.values());
        JButton startMultiPlayerButton = new JButton("Start Multiplayer Game");
        startSinglePlayerButton = new JButton("Play with Computer");

        setLayout(new GridLayout(4, 1)); // Adjust for the new button
        JPanel panel1 = new JPanel();
        panel1.add(difficultyLabel);
        panel1.add(difficultyComboBox);
        JPanel panel2 = new JPanel();
        panel2.add(startMultiPlayerButton);
        JPanel panel3 = new JPanel();
        panel3.add(startSinglePlayerButton);

        add(panel1);
        add(panel2);
        add(panel3);

        startMultiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(false); // false for multiplayer mode
            }
        });

        startSinglePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true); // true for single-player mode
            }
        });
    }

    private void startGame(boolean isSinglePlayer) {
        DifficultyLevel selectedDifficulty = (DifficultyLevel) difficultyComboBox.getSelectedItem();

        // Start the game with the selected difficulty and mode
        Game game = Game.getInstance(selectedDifficulty, isSinglePlayer);
        JFrame gameFrame = new JFrame("Pong Game");
        gameFrame.setSize(800, 600);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.getContentPane().add(new GamePanel(game));

        HomePage.this.setVisible(false);
        gameFrame.setVisible(true);
        System.out.println("Game Started - Difficulty: " + selectedDifficulty + ", Mode: " + (isSinglePlayer ? "Single Player" : "Multiplayer"));
    }
}

