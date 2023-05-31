package com.example.ludikgames.repository;

import com.example.ludikgames.model.User;

import java.util.List;

public interface UserCustomRepository {
    List<User> findModeratorsByMinimumCountOfChats();
}
