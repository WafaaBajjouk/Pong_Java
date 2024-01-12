package com.pongame.graphics;

import com.pongame.classes.Player;
import com.pongame.config.DifficultyLevel;
import com.pongame.game.Game;
import com.pongame.graphics.PlayerAccountForm;
import com.pongame.graphics.LoginForm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class HomePage extends JFrame {
    private final JComboBox<DifficultyLevel> difficultyComboBox;
    private final JButton startSinglePlayerButton;
    private final JButton startMultiPlayerButton;
    private final JButton createAccountButton;
    private final JButton loginButton;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public HomePage(Player player) {
        ImageIcon backgroundImage = new ImageIcon("/Users/wafaabajjouk/Desktop/Pong_Java/src/main/java/com/pongame/utils/back.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);


        backgroundLabel.setLayout(new BorderLayout());

        setSize(WIDTH, HEIGHT);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        difficultyComboBox = new JComboBox<>(DifficultyLevel.values());
        difficultyComboBox.setPreferredSize(new Dimension(150, 30));
        Dimension buttonSize = new Dimension(150, 30);
        startSinglePlayerButton = new JButton("Play with Computer");
        startSinglePlayerButton.setPreferredSize(buttonSize);
        startMultiPlayerButton = new JButton("Start Multiplayer Game");
        startMultiPlayerButton.setPreferredSize(buttonSize);
        createAccountButton = new JButton("Create Account");
        loginButton = new JButton("Login");
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        centerPanel.add(new JLabel("Select Difficulty:"), gbc);
        gbc.gridx++;
        centerPanel.add(difficultyComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 1;
        gbc.insets = new Insets(0, 0, 0, 10);
        centerPanel.add(startMultiPlayerButton, gbc);
        gbc.gridx++;
        centerPanel.add(startSinglePlayerButton, gbc);

        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        bottomPanel.add(createAccountButton);
        bottomPanel.add(loginButton);

        if (player != null) {
            createAccountButton.setVisible(false);
            loginButton.setVisible(false);
        }
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                PlayerAccountForm accountForm = new PlayerAccountForm();
                accountForm.setVisible(true);
            }
        });

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoginForm loginForm = new LoginForm();
                loginForm.setVisible(true);
            }
        });

        // Add the centerPanel to the backgroundLabel
        backgroundLabel.add(centerPanel, BorderLayout.CENTER);
        backgroundLabel.add(bottomPanel, BorderLayout.SOUTH);

        // Add the backgroundLabel to the JFrame
        getContentPane().add(backgroundLabel);

        startMultiPlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(false, player);
            }
        });

        startSinglePlayerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startGame(true, player);
            }
        });
    }

    private void startGame(boolean isSinglePlayer, Player player) {
        DifficultyLevel selectedDifficulty = (DifficultyLevel) difficultyComboBox.getSelectedItem();
        Game game = new Game(selectedDifficulty, isSinglePlayer, player);
        JFrame gameFrame = new JFrame("Pong Game");
        gameFrame.setSize(WIDTH, HEIGHT);
        gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameFrame.setLocationRelativeTo(null);
        gameFrame.getContentPane().add(new GamePanel(game, player));

        HomePage.this.setVisible(false);
        gameFrame.setVisible(true);
        System.out.println("Game Started - Difficulty: " + selectedDifficulty + ", Mode: " + (isSinglePlayer ? "Single Player" : "Multiplayer"));
    }
}
