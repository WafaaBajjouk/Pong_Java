package com.pongame.graphics;

import com.pongame.classes.Player;

import com.pongame.interfaces.IPlayerDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


    public class LoginForm extends JFrame {
        private JTextField nameField;
        private JPasswordField passwordField;
        private IPlayerDAO playerDAO;

        public LoginForm(IPlayerDAO playerDAO) {
            this.playerDAO = playerDAO;

            setTitle("Login Form");
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(400, 150);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(3, 2));

            JLabel nameLabel = new JLabel("Name:");
            nameField = new JTextField();

            JLabel passwordLabel = new JLabel("Password:");
            passwordField = new JPasswordField();

            JButton loginButton = new JButton("Login");
            loginButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String enteredName = nameField.getText();
                    char[] enteredPasswordChars = passwordField.getPassword();
                    String enteredPassword = new String(enteredPasswordChars);

                    Player user = playerDAO.authenticatePlayer(enteredName, enteredPassword);
                    if (user != null) {
                        JOptionPane.showMessageDialog(LoginForm.this, "Login successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                        openHomePage(user);
                        dispose();
                    } else {
                        JOptionPane.showMessageDialog(LoginForm.this, "Login failed. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
                    }

                    nameField.setText("");
                    passwordField.setText("");
                    dispose();
                }
            });

            panel.add(nameLabel);
            panel.add(nameField);
            panel.add(passwordLabel);
            panel.add(passwordField);
            panel.add(new JLabel());
            panel.add(loginButton);

            add(panel);
            setLocationRelativeTo(null);

    }

    private void openHomePage(Player user) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                HomePage homePage = new HomePage(user);
                System.out.println("Logging and go home page of "+user.getName());
                homePage.setVisible(true);
            }
        });
    }


}