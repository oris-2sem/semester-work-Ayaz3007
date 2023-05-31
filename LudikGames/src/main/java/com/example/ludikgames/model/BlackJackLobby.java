package com.example.ludikgames.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BlackJackLobby {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;

    @OneToOne()
    @JoinColumn(name = "player1")
    private User player1;

    @OneToOne
    @JoinColumn(name = "player2")
    private User player2;

    @OneToOne
    @JoinColumn(name = "player3")
    private User player3;
}
