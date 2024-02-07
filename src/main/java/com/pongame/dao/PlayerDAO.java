package com.pongame.dao;

import com.pongame.classes.Player;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import com.pongame.interfaces.IPlayerDAO;

public class PlayerDAO implements IPlayerDAO {
    private Connection connection;

    public PlayerDAO(Connection connection) {
        this.connection = connection;
    }

    public boolean createPlayer(Player player) {
        String sql = "INSERT INTO Player (name, birthday, password) VALUES (?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, player.getName());
            preparedStatement.setString(2, player.getBirthday());
            preparedStatement.setString(3, player.getPassword());

            int rowsInserted = preparedStatement.executeUpdate();
            return true;
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
                String storedPassword = resultSet.getString("password");

                if (password.equals(storedPassword)) {
                    int id = resultSet.getInt("Id");
                    String playerName = resultSet.getString("name");
                    String playerBirthday = resultSet.getString("birthday");
                    Player player = new Player(playerName, playerBirthday, storedPassword);
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

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, newPassword);
            preparedStatement.setInt(2, playerId);

            int rowsUpdated = preparedStatement.executeUpdate();
            return rowsUpdated > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
