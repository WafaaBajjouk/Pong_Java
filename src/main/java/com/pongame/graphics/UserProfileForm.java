package com.pongame.graphics;

import com.pongame.classes.Player;

import javax.swing.*;
import java.awt.*;
import java.text.SimpleDateFormat;

public class UserProfileForm extends JFrame {
    private Player user;

    public UserProfileForm(Player user) {
        this.user = user;

        setTitle("User Profile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2));

        JLabel nameLabel = new JLabel("Name:");
        JLabel nameValueLabel = new JLabel(user.getName());

        JLabel birthdayLabel = new JLabel("Birthday:");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        JLabel birthdayValueLabel = new JLabel(dateFormat.format(user.getBirthday()));

        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            dispose();
        });

        panel.add(nameLabel);
        panel.add(nameValueLabel);
        panel.add(birthdayLabel);
        panel.add(birthdayValueLabel);
        panel.add(new JLabel());
        panel.add(logoutButton);

        add(panel);
        setLocationRelativeTo(null);
    }
}
