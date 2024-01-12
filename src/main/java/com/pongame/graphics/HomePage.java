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
    private final JButton profileButton;
    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public HomePage(Player player) {
        ImageIcon backgroundImage = new ImageIcon("/Users/wafaabajjouk/Desktop/Pong_Java/src/main/java/com/pongame/utils/back.png");
        JLabel backgroundLabel = new JLabel(backgroundImage);

        backgroundLabel.setLayout(new OverlayLayout(backgroundLabel));

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
        profileButton = new JButton("Profile");

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();

        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets = new Insets(0, 0, 20, 0);
        gbc.anchor = GridBagConstraints.CENTER;

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

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.add(profileButton);
        profileButton.setVisible(player != null); // Show profile button if player is logged in

        JPanel overlayPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        overlayPanel.setOpaque(false);
        overlayPanel.add(createAccountButton);
        overlayPanel.add(loginButton);

        if (player != null) {
            createAccountButton.setVisible(false);
            loginButton.setVisible(false);
        }

        createAccountButton.addActionListener(e -> {
            PlayerAccountForm accountForm = new PlayerAccountForm();
            accountForm.setVisible(true);
        });

        loginButton.addActionListener(e -> {
            LoginForm loginForm = new LoginForm();
            loginForm.setVisible(true);
        });

        profileButton.addActionListener(e -> {
            UserProfileForm userProfileForm = new UserProfileForm(player);
            userProfileForm.setVisible(true);
            System.out.println("Profile " + player.getName() + ", Birthday: " +player.getBirthday());

        });

        backgroundLabel.add(topPanel, BorderLayout.NORTH);
        backgroundLabel.add(overlayPanel);
        backgroundLabel.add(centerPanel, BorderLayout.CENTER);

        getContentPane().add(backgroundLabel);

        startMultiPlayerButton.addActionListener(e -> {
            startGame(false, player);
        });

        startSinglePlayerButton.addActionListener(e -> {
            startGame(true, player);
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
