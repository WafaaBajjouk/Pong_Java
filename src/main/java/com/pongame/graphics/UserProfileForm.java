package com.pongame.graphics;

import com.pongame.classes.Player;
import com.pongame.dao.PlayerDAO;
import com.pongame.database.DbConnection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;

public class UserProfileForm extends JFrame {
    private Player user;
    public JTextField nameTextField;
    public JTextField birthdayTextField;
    public JPasswordField passwordField;

    public UserProfileForm(Player user) {
        this.user = user;
        initUI();
    }

    private void initUI() {
        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLayout(new BorderLayout());
        add(createTopPanel(), BorderLayout.NORTH);
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
