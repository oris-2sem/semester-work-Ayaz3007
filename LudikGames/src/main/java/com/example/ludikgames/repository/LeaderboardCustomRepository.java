package com.example.ludikgames.repository;

import com.example.ludikgames.model.Leaderboard;

import java.util.List;

public interface LeaderboardCustomRepository {
    List<Leaderboard> findAllByNotBannedUsers();
}
