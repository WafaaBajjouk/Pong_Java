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

    public HomePage() {
        setTitle("Pong Game Home Page");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel difficultyLabel = new JLabel("Select Difficulty:");
        difficultyComboBox = new JComboBox<>(DifficultyLevel.values());
        JButton startButton = new JButton("Start Game");
        setLayout(new GridLayout(3, 1));
        JPanel panel1 = new JPanel();
        panel1.add(difficultyLabel);
        panel1.add(difficultyComboBox);
        JPanel panel2 = new JPanel();
        panel2.add(startButton);

        add(panel1);
        add(panel2);

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DifficultyLevel selectedDifficulty = (DifficultyLevel) difficultyComboBox.getSelectedItem();

                // Start the game with the selected difficulty
                Game game = Game.getInstance(selectedDifficulty);
                JFrame gameFrame = new JFrame("Pong Game");
                gameFrame.setSize(800, 600);
                gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gameFrame.setLocationRelativeTo(null);
                gameFrame.getContentPane().add(new GamePanel(game));

                HomePage.this.setVisible(false);
                gameFrame.setVisible(true);
                System.out.println("Selected Difficulty: " + selectedDifficulty);
            }
        });
    }

}
