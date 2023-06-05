package com.example.ludikgames.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Check;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.UUID;

@Entity(name = "users")
@AllArgsConstructor
@Builder
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
            name = "favourite_slots",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "slot_id")}
    )
    private Set<Slot> slots = new HashSet<>();

    @OneToOne(mappedBy = "user")
    private Leaderboard place;

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

    public boolean isActive() {
        return this.state.equals(State.ACTIVE);
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
        ACTIVE,
        BANNED
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public void setRank(Rank rank) {
        this.rank = rank;
    }

    public void setBattlePassProgress(Integer battlePassProgress) {
        this.battlePassProgress = battlePassProgress;
    }

    public void setBalance(Integer balance) {
        this.balance = balance;
    }

    public void setSlots(Set<Slot> slots) {
        this.slots = slots;
    }

    public void setPlace(Leaderboard place) {
        this.place = place;
    }

    public void setChatMessage(Set<ChatMessage> chatMessage) {
        this.chatMessage = chatMessage;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setState(State state) {
        this.state = state;
    }

    public void setChats(List<SupportChat> chats) {
        this.chats = chats;
    }

    public String getNickname() {
        return nickname;
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public Rank getRank() {
        return rank;
    }

    public Integer getBattlePassProgress() {
        return battlePassProgress;
    }

    public Integer getBalance() {
        return balance;
    }

    public Set<Slot> getSlots() {
        return slots;
    }

    public Leaderboard getPlace() {
        return place;
    }

    public Set<ChatMessage> getChatMessage() {
        return chatMessage;
    }

    public Role getRole() {
        return role;
    }

    public State getState() {
        return state;
    }

    public List<SupportChat> getChats() {
        return chats;
    }
}
