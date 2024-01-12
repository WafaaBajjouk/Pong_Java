package com.pongame.graphics;

import com.pongame.classes.Player;
import com.pongame.dao.GameDAO;
import com.pongame.classes.Match;
import com.pongame.dao.PlayerDAO;
import com.pongame.database.DbConnection;
import com.pongame.graphics.HomePage;

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
    private JTextField nameTextField;
    private JTextField birthdayTextField;
    private JPasswordField passwordField;

    public UserProfileForm(Player user) {
        this.user = user;
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);

        // Load background image
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

        // Use GridBagLayout for organized layout
        GridBagLayout gridBagLayout = new GridBagLayout();
        panel.setLayout(gridBagLayout);
        GridBagConstraints gridBagConstraints = new GridBagConstraints();
        gridBagConstraints.fill = GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new Insets(5, 5, 5, 5);
        nameTextField = new JTextField(user.getName());
        nameTextField.setFont(new Font("Arial", Font.BOLD, 18));
        nameTextField.setEditable(false);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 0;
        panel.add(new JLabel("Name:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        panel.add(nameTextField, gridBagConstraints);

        birthdayTextField = new JTextField(user.getBirthday() != null ? user.getBirthday() : "N/A");
        birthdayTextField.setFont(new Font("Arial", Font.BOLD, 18));
        birthdayTextField.setEditable(false);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 1;
        gridBagConstraints.gridwidth = 1;
        panel.add(new JLabel("Birthday:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        panel.add(birthdayTextField, gridBagConstraints);

        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.BOLD, 18));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 2;
        gridBagConstraints.gridwidth = 1;
        panel.add(new JLabel("Password:"), gridBagConstraints);
        gridBagConstraints.gridx = 1;
        gridBagConstraints.gridwidth = 2;
        panel.add(passwordField, gridBagConstraints);

        // Create a JButton for changing the password
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 16));
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 3;
        gridBagConstraints.gridwidth = 3;
        panel.add(changePasswordButton, gridBagConstraints);
        changePasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String newPassword = JOptionPane.showInputDialog(UserProfileForm.this, "Enter new password:");
                if (newPassword != null) {
                    PlayerDAO playerDAO = new PlayerDAO(DbConnection.getInstance());
                    boolean passwordChanged = playerDAO.changePassword(user.getId(), newPassword);
                    if (passwordChanged) {
                        JOptionPane.showMessageDialog(UserProfileForm.this, "Password changed successfully.");
                    } else {
                        JOptionPane.showMessageDialog(UserProfileForm.this, "Failed to change password.");
                    }
                }
            }

        });

        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        Dimension buttonSize = new Dimension(100, 40); // Smaller button (100x40)
        logoutButton.setPreferredSize(buttonSize);
        ImageIcon logoutIcon = new ImageIcon("/Users/wafaabajjouk/Desktop/Pong_Java/src/main/java/com/pongame/pictures/logout.png");
        // Resize the icon
        Image iconImage = logoutIcon.getImage();
        Image resizedIconImage = iconImage.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
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

        gridBagConstraints.gridx = 2;
        gridBagConstraints.gridy = 4;
        gridBagConstraints.gridwidth = 1;
        panel.add(logoutButton, gridBagConstraints);
        JTable gamesTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(gamesTable);
        gridBagConstraints.gridx = 0;
        gridBagConstraints.gridy = 5;
        gridBagConstraints.gridwidth = 3;
        gridBagConstraints.fill = GridBagConstraints.BOTH;
        gridBagConstraints.weightx = 1.0;
        gridBagConstraints.weighty = 1.0;
        panel.add(scrollPane, gridBagConstraints);
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

        add(panel);
        setLocationRelativeTo(null);
    }


}
