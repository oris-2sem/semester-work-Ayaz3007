package com.example.ludikgames.repository;

import com.example.ludikgames.model.SupportMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SupportMessageRepository extends JpaRepository<SupportMessage, UUID> {
}
