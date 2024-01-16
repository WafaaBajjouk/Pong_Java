package com.pongame.database;

import com.pongame.utils.Constants;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConnection {
    private static Connection connection;

    private DbConnection() {
        // Singleton Princ:Private constructor to prevent external instantiation
    }

    public static Connection getInstance() {
        if (connection == null) {
            synchronized (DbConnection.class) {
                if (connection == null) {
                    try {
                        Class.forName("com.mysql.cj.jdbc.Driver");
                        connection = DriverManager.getConnection(Constants.DB_URL, Constants.DB_USER, Constants.DB_PASSWORD);
                        System.out.println("Connection Success");
                    } catch (SQLException | ClassNotFoundException e) {
                        System.err.println("SQL Exception.");
                        e.printStackTrace();
                    }
                }
            }
        }
        return connection;
    }

    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Connection Closed");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        Connection con = DbConnection.getInstance();
        DbConnection.closeConnection();
    }
}
