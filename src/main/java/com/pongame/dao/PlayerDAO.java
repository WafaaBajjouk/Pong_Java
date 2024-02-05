package com.pongame.dao;

import com.pongame.classes.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.mindrot.jbcrypt.BCrypt;


public class PlayerDAO {
    private Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createPlayer(Player player) {
        String sql = "INSERT INTO Player (name, birthday, password) VALUES (?, ?, ?)";
        String hashedPassword = BCrypt.hashpw(player.getPassword(), BCrypt.gensalt());

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, player.getBirthday());
            preparedStatement.setString(3, hashedPassword);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public Player authenticatePlayer(String name, String password) {
        String sql = "SELECT * FROM Player WHERE name = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                int id = resultSet.getInt("Id");
                String playerName = resultSet.getString("name");
                String playerBirthday = resultSet.getString("birthday");
                String storedHashedPassword = resultSet.getString("password");

                if (BCrypt.checkpw(password, storedHashedPassword)) {
                    Player player = new Player(playerName, playerBirthday, storedHashedPassword);
                    player.setId(id);
                    return player;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }
    public boolean changePassword(int playerId, String newPassword) {
        String sql = "UPDATE Player SET password = ? WHERE Id = ?";
        String hashedNewPassword = BCrypt.hashpw(newPassword, BCrypt.gensalt());

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, hashedNewPassword);
            preparedStatement.setInt(2, playerId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
