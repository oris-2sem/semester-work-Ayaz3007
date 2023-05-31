package com.example.ludikgames.service.impl;

import com.example.ludikgames.model.BlackJackLobby;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.BlackJackRepository;
import com.example.ludikgames.repository.UsersRepository;
import com.example.ludikgames.service.BlackJackService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BlackJackServiceImpl implements BlackJackService {


    private final UsersRepository usersRepository;
    private final BlackJackRepository blackJackRepository;

    private final List<Card> cards = Card.pullCards();

    @Override
    public BlackJackLobby createLobby(String email) {
        User user = usersRepository.findByEmail(email).orElseThrow();

        BlackJackLobby lobby = BlackJackLobby.builder()
                .player1(user)
                .build();
        blackJackRepository.save(lobby);

        return lobby;
    }

    @Override
    public BlackJackLobby getLobbyById(UUID uuid) {
        return blackJackRepository.getReferenceById(uuid);
    }

    @AllArgsConstructor
    private static class Card {
        private String suit;
        private Integer value;
        private static final String[] suits = new String[]{"spades", "hearts", "diamonds", "clubs"};

        private static List<Card> pullCards() {
            List<Card> cards = new ArrayList<>();
            for(String suit : suits) {
                for(int i = 1; i <= 14; i++){
                    cards.add(new Card(suit, i));
                }
            }
            Collections.shuffle(cards);
            return cards;
        }
    }
}
