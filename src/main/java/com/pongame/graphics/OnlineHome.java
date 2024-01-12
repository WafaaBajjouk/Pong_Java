package com.pongame.graphics;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class OnlineHome extends JFrame {

    public OnlineHome() {
        setTitle("Online Pong Game");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JButton btnConnectServer = new JButton("Connect to Server");
        JButton btnCreateServer = new JButton("Create a Server");
        btnConnectServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
//                new ServerConnectionWindow().setVisible(true);
            }
        });

        btnCreateServer.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });

        setLayout(new FlowLayout());
        add(btnConnectServer);
        add(btnCreateServer);
    }
}
