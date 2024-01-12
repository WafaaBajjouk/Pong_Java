package com.pongame.graphics;

import com.pongame.classes.Player;
import com.pongame.dao.GameDAO;
import com.pongame.classes.Match;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class UserProfileForm extends JFrame {
    private Player user;

    public UserProfileForm(Player user) {
        this.user = user;
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JLabel nameLabel = new JLabel("Name: " + user.getName());
        JLabel birthdayLabel = new JLabel("Birthday: " + (user.getBirthday() != null ? user.getBirthday() : "N/A"));

        JButton logoutButton = new JButton("Logout");

        logoutButton.addActionListener(e -> {
            dispose();
            HomePage homepage = new HomePage(null);
            homepage.setVisible(true);
        });

        panel.add(nameLabel, BorderLayout.NORTH);
        panel.add(birthdayLabel, BorderLayout.CENTER);

        // Create a table to display the games
        JTable gamesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(gamesTable);
        panel.add(scrollPane, BorderLayout.SOUTH);

        // Fetch and display the games
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
