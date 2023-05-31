package com.example.ludikgames.repository;

import com.example.ludikgames.model.SupportChat;
import com.example.ludikgames.model.User;

import java.util.List;

public interface SupportChatCustomRepository {
    List<SupportChat> findChatByUser(User user);
}
