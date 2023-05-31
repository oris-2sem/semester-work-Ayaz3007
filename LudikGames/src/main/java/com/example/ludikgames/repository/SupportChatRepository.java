package com.example.ludikgames.repository;

import com.example.ludikgames.model.SupportChat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupportChatRepository extends JpaRepository<SupportChat, UUID> {
}
