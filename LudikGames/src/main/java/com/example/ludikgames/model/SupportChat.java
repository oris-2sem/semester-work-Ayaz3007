package com.example.ludikgames.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.List;
import java.util.UUID;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SupportChat {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "moderator_id")
    private User moderator;

    @OneToMany(mappedBy = "supportChat", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<SupportMessage> messages;
}
