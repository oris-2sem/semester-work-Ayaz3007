package com.example.ludikgames.service;

import com.example.ludikgames.model.BlackJackLobby;
import org.springframework.stereotype.Service;

import java.util.UUID;

public interface BlackJackService {
    BlackJackLobby createLobby(String email);
    BlackJackLobby getLobbyById(UUID uuid);
}
