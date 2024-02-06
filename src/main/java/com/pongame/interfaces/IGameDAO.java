package com.pongame.interfaces;

import com.pongame.classes.Match;
import java.sql.SQLException;
import java.util.List;

public interface IGameDAO {
    void saveGame(Match game) throws SQLException;
    List<Match> getGamesByPlayerId(int playerId) throws SQLException;
}
