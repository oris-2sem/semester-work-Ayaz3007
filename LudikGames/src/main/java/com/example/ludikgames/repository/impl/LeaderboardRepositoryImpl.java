package com.example.ludikgames.repository.impl;

import com.example.ludikgames.model.Leaderboard;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.LeaderboardCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class LeaderboardRepositoryImpl implements LeaderboardCustomRepository {
    @PersistenceContext
    private final EntityManager entityManager;

    @Override
    public List<Leaderboard> findAllByNotBannedUsers() {
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Leaderboard> criteriaQuery = criteriaBuilder.createQuery(Leaderboard.class);
        Root<Leaderboard> placeRoot = criteriaQuery.from(Leaderboard.class);

        Join<Leaderboard, User> userJoin = placeRoot.join("user", JoinType.INNER);
        Predicate statusPredicate = criteriaBuilder.notEqual(userJoin.get("state"), User.State.BANNED);

        criteriaQuery.select(placeRoot).where(statusPredicate);
        criteriaQuery.orderBy(criteriaBuilder.desc(placeRoot.get("points")));

        return entityManager.createQuery(criteriaQuery).getResultList();
    }
}
