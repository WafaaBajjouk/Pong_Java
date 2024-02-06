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
        String sql = "INSERT INTO Game (date_of_game, isSinglePlayerMode, score,win, player_id) VALUES (?, ?, ?, ?,?)";

        try (PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            statement.setDate(1, new java.sql.Date(System.currentTimeMillis())); // Save the current date
            statement.setBoolean(2, game.isSinglePlayerMode());
            statement.setInt(3, game.getScore());
            statement.setBoolean(4, game.isWin());
            statement.setInt(5, game.getPlayerId());

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
        String sql = "SELECT * FROM Game WHERE Id = ?";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, playerId);

            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int gameId = resultSet.getInt("Id");
                    Date date = resultSet.getDate("date_of_game");
                    int score = resultSet.getInt("score");
                    boolean isSinglePlayerMode = resultSet.getBoolean("isSinglePlayerMode");
                    boolean win = resultSet.getBoolean("win");
                    Match game = new Match( isSinglePlayerMode ,score,gameId, win);
                    game.setId(gameId);
                    game.setDate(date);
                    games.add(game);
                }
            }
        }

        return games;
    }
}


