package com.example.ludikgames.service;

import com.example.ludikgames.dto.PlaceDto;
import com.example.ludikgames.model.User;

import java.util.List;

public interface LeaderboardService {
    void addUser(User user);

    List<PlaceDto> getUsersList();
}
