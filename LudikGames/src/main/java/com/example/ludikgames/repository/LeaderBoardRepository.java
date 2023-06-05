package com.example.ludikgames.repository;

import com.example.ludikgames.model.Leaderboard;
import com.example.ludikgames.model.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface LeaderBoardRepository extends JpaRepository<Leaderboard, UUID> {
    List<Leaderboard> findAllByOrderByPointsDesc();
}
