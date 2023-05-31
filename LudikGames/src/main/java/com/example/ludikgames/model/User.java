package com.example.ludikgames.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @GenericGenerator(name="UUID", strategy = "org.hibernate.id.UUIDGenerator")
    private UUID uuid;
    private String nickname;
    private String hashPassword;
    private String email;
    private String phone_number;

    @Enumerated(EnumType.STRING)
    private Rank rank;
    private Integer battlePassProgress;
    private Integer balance;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "Favourite_slots",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "slot_id")}
    )
    private Set<Slot> slots = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Leaderboard place;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "big_wins_id")
    private BigWinsList bigWinsPlace;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private Set<ChatMessage> chatMessage;

    private Role role;
    private State state;

    @OneToMany(mappedBy = "moderator")
    @Check(constraints = "role = 2")
    private List<SupportChat> chats;

    public boolean isBanned() {
        return this.state.equals(State.BANNED);
    }

    public boolean isConfirmed() {
        return this.state.equals(State.CONFIRMED);
    }


    public enum Rank {
        BRONZE,
        SILVER,
        GOLD,
        DIAMOND,
        LEGENDARY
    }

    public enum Role {
        ADMIN,
        USER,
        MODERATOR
    }

    public enum State {
        NOT_CONFIRMED,
        CONFIRMED,
        DELETED,
        BANNED
    }

}
