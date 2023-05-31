package com.example.ludikgames.repository;

import com.example.ludikgames.model.Slot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface SlotRepository extends JpaRepository<Slot, UUID> {
}
