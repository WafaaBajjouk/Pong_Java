package com.pongame.interfaces;

import com.pongame.classes.Player;

public interface IPlayerDAO {
    boolean createPlayer(Player player);
    Player authenticatePlayer(String name, String password);
    boolean changePassword(int playerId, String newPassword);
}
