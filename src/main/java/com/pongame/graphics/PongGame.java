package com.pongame.graphics;

public class PongGame {

    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                HomePage homePage = new HomePage(null);
                homePage.setVisible(true);
            }
        });
    }
}
