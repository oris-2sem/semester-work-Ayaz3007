package com.example.ludikgames.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.Instant;
import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private String message;
    @CreationTimestamp
    private Instant dateTime;
}
