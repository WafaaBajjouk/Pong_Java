package com.pongame.dao;

import com.pongame.classes.Match;
import java.sql.*;

public class GameDAO {
    private Connection connection;

    public GameDAO(Connection connection) {
        this.connection = connection;
    }

    public void saveGame(Match game) throws SQLException {
        String sql = "INSERT INTO Game (date, isSinglePlayerMode, score, player_id) VALUES (?, ?, ?, ?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new java.sql.Date(System.currentTimeMillis())); // Save the current date
            statement.setBoolean(2, game.isSinglePlayerMode());
            statement.setInt(3, game.getScore());
            statement.setInt(4, game.getPlayerId());

            int affectedRows = statement.executeUpdate();


            if (affectedRows == 0) {
                throw new SQLException("Creating game failed, no rows affected.");
            }

            try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    game.setId(generatedKeys.getInt(1));
                } else {
                    throw new SQLException("Creating game failed, no ID obtained.");
                }
            }
        }
    }

}
