package com.pongame.graphics;

import com.pongame.classes.Player;
import com.pongame.dao.GameDAO;
import com.pongame.classes.Match;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.io.File;

public class UserProfileForm extends JFrame {
    private Player user;

    public UserProfileForm(Player user) {
        this.user = user;
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        Image backgroundImage = null;
        try {
            backgroundImage = ImageIO.read(new File("/Users/wafaabajjouk/Desktop/Pong_Java/src/main/java/com/pongame/pictures/black.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image finalBackgroundImage = backgroundImage;
        JPanel panel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                if (finalBackgroundImage != null) {
                    g.drawImage(finalBackgroundImage, 0, 0, getWidth(), getHeight(), this);
                }
            }
        };
        panel.setLayout(new BorderLayout());
        JLabel nameLabel = new JLabel("Name: " + user.getName());
        nameLabel.setFont(new Font("Arial", Font.BOLD, 18));
        nameLabel.setForeground(Color.WHITE);
        JLabel birthdayLabel = new JLabel("Birthday: " + (user.getBirthday() != null ? user.getBirthday() : "N/A"));
        birthdayLabel.setFont(new Font("Arial", Font.BOLD, 18));
        birthdayLabel.setForeground(Color.WHITE);
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        Dimension buttonSize = new Dimension(50, 10);
        logoutButton.setPreferredSize(buttonSize);
        ImageIcon logoutIcon = new ImageIcon("/Users/wafaabajjouk/Desktop/Pong_Java/src/main/java/com/pongame/pictures/logout.png");
        Image iconImage = logoutIcon.getImage();
        Image resizedIconImage = iconImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH); // Smaller icon (20x20)
        ImageIcon resizedLogoutIcon = new ImageIcon(resizedIconImage);
        logoutButton.setIcon(resizedLogoutIcon);
        logoutButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                HomePage homepage = new HomePage(null);
                homepage.setVisible(true);
            }
        });
        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(birthdayLabel, BorderLayout.CENTER);
        JTable gamesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(gamesTable);
        panel.add(scrollPane, BorderLayout.SOUTH);
        if (user != null) {
            try {
                Connection connection = com.pongame.database.DbConnection.getInstance();
                GameDAO gameDAO = new GameDAO(connection);

                List<Match> games = gameDAO.getGamesByPlayerId(user.getId());
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Game ID");
                model.addColumn("Date");
                model.addColumn("Single Player");
                model.addColumn("Score");

                for (Match game : games) {
                    model.addRow(new Object[]{
                            game.getId(),
                            game.getDate(),
                            game.isSinglePlayerMode(),
                            game.getScore()
                    });
                }

                gamesTable.setModel(model);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        panel.add(logoutButton, BorderLayout.EAST);

        add(panel);
        setLocationRelativeTo(null);
    }
}
