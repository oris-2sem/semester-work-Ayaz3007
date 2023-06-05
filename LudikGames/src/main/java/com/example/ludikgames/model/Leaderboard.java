package com.example.ludikgames.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.UUID;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Leaderboard {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private User user;
    private Integer points;
}
