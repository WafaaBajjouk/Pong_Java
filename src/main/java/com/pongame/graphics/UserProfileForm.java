package com.pongame.graphics;

import com.pongame.classes.Match;
import com.pongame.classes.Player;
import com.pongame.dao.GameDAO;
import com.pongame.dao.PlayerDAO;
import com.pongame.database.DbConnection;
import com.pongame.graphics.HomePage;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.imageio.ImageIO;

public class UserProfileForm extends JFrame {
    private Player user;
    private JTextField nameTextField;
    private JTextField birthdayTextField;
    private JPasswordField passwordField;

    public UserProfileForm(Player user) {
        this.user = user;
        initUI();
    }

    private void initUI() {
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
        add(createCenterPanel(), BorderLayout.CENTER);
        add(createBottomPanel(), BorderLayout.SOUTH);
        setLocationRelativeTo(null);
    }

    private JPanel createTopPanel() {
        JPanel topPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);
        // Name
        nameTextField = new JTextField(user.getName());
        nameTextField.setFont(new Font("Arial", Font.BOLD, 18));
        nameTextField.setEditable(false);
        gbc.gridx = 0; gbc.gridy = 0;
        topPanel.add(new JLabel("Name:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        topPanel.add(nameTextField, gbc);
        // Birthday
        birthdayTextField = new JTextField(user.getBirthday() != null ? user.getBirthday() : "N/A");
        birthdayTextField.setFont(new Font("Arial", Font.BOLD, 18));
        birthdayTextField.setEditable(false);
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1;
        topPanel.add(new JLabel("Birthday:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        topPanel.add(birthdayTextField, gbc);
        // Password
        passwordField = new JPasswordField();
        passwordField.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0; gbc.gridy = 2; gbc.gridwidth = 1;
        topPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 2;
        topPanel.add(passwordField, gbc);

        return topPanel;
    }

    private JPanel createCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout());
        JTable gamesTable = new JTable();
        centerPanel.add(new JScrollPane(gamesTable), BorderLayout.CENTER);

        if (user != null) {
            try {
                Connection connection = DbConnection.getInstance();
                GameDAO gameDAO = new GameDAO(connection);
                List<Match> games = gameDAO.getGamesByPlayerId(user.getId());
                DefaultTableModel model = new DefaultTableModel();
                model.addColumn("Game ID");
                model.addColumn("Date");
                model.addColumn("Single Player");
                model.addColumn("Score");

                for (Match game : games) {
                    model.addRow(new Object[]{game.getId(), game.getDate(), game.isSinglePlayerMode(), game.getScore()});
                }

                gamesTable.setModel(model);
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }

        return centerPanel;
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        // Change Password Button
        JButton changePasswordButton = new JButton("Change Password");
        changePasswordButton.setFont(new Font("Arial", Font.BOLD, 16));
        changePasswordButton.addActionListener(e -> handleChangePassword());
        bottomPanel.add(changePasswordButton);
        // Logout Button
        JButton logoutButton = createLogoutButton();
        bottomPanel.add(logoutButton);

        return bottomPanel;
    }

    private JButton createLogoutButton() {
        JButton logoutButton = new JButton("Logout");
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setPreferredSize(new Dimension(100, 40));
        ImageIcon logoutIcon = new ImageIcon("/Users/wafaabajjouk/Desktop/Pong_Java/src/main/java/com/pongame/pictures/logout.png");
        Image iconImage = logoutIcon.getImage().getScaledInstance(20, 20, Image.SCALE_SMOOTH);
        logoutButton.setIcon(new ImageIcon(iconImage));
        logoutButton.addActionListener(e -> logout());

        return logoutButton;
    }

    private void handleChangePassword() {
        String newPassword = JOptionPane.showInputDialog(this, "Enter new password:");
        if (newPassword != null) {
            PlayerDAO playerDAO = new PlayerDAO(DbConnection.getInstance());
            boolean passwordChanged = playerDAO.changePassword(user.getId(), newPassword);
            if (passwordChanged) {
                JOptionPane.showMessageDialog(this, "Password changed successfully.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to change password.");
            }
        }
    }

    private void logout() {
        dispose();
        HomePage homepage = new HomePage(null);
        homepage.setVisible(true);
    }


}
