package com.example.ludikgames.repository;

import com.example.ludikgames.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MessageRepository extends JpaRepository<ChatMessage, UUID> {

}
