package com.pongame.graphics;

import com.pongame.classes.Player;
import com.pongame.dao.PlayerDAO;
import com.pongame.database.DbConnection;
import com.pongame.interfaces.IPlayerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;

public class PlayerAccountForm extends JFrame {
    public JTextField nameField;
    public JTextField birthdayField;
    public JPasswordField passwordField;

    private IPlayerDAO playerDAO;
    public JButton registerButton;

    public PlayerAccountForm() {
        setTitle("Player Account Registration");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        nameField = new JTextField();

        JLabel birthdayLabel = new JLabel("Birthday (yyyy-MM-dd):");
        birthdayField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        passwordField = new JPasswordField();

         registerButton = new JButton("Register");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String birthday = birthdayField.getText(); // Retrieve as a string
                char[] passwordChars = passwordField.getPassword();
                String password = new String(passwordChars);
                Player newPlayer = new Player(name, birthday, password);
                 playerDAO = new PlayerDAO(DbConnection.getInstance());
                boolean registrationSuccess = playerDAO.createPlayer(newPlayer);

                if (registrationSuccess) {
                    JOptionPane.showMessageDialog(PlayerAccountForm.this, "Registration successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    nameField.setText("");
                    birthdayField.setText("");
                    passwordField.setText("");
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(PlayerAccountForm.this, "Registration failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }

        });

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(birthdayLabel);
        panel.add(birthdayField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(new JLabel());
        panel.add(registerButton);

        add(panel);
        setLocationRelativeTo(null);
    }


}
