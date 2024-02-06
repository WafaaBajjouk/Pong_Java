package com.pongame.dao;

import com.pongame.classes.Match;
import com.pongame.interfaces.IGameDAO;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class GameDAO  implements IGameDAO {
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


//    history of games , i need it for profile page

    public List<Match> getGamesByPlayerId(int playerId) throws SQLException {
        List<Match> games = new ArrayList<>();
        String sql = "SELECT * FROM Game WHERE player_id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int gameId = resultSet.getInt("id");
                    Date date = resultSet.getDate("date");
                    boolean isSinglePlayerMode = resultSet.getBoolean("isSinglePlayerMode");
                    int score = resultSet.getInt("score");
                    Match game = new Match( isSinglePlayerMode, score, playerId);
                    game.setId(gameId);
                    game.setDate(date);
                    games.add(game);
                }
            }
        }

        return games;
    }
}

