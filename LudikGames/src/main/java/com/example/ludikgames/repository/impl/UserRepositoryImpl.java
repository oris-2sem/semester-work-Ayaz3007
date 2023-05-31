package com.example.ludikgames.repository.impl;

import com.example.ludikgames.model.SupportChat;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.UserCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserCustomRepository {
    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<User> findModeratorsByMinimumCountOfChats() {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<User> cq = cb.createQuery(User.class);
        Root<User> root = cq.from(User.class);

        Join<User, SupportChat> chatJoin = root.join("chats", JoinType.LEFT);

        cq.select(root).where(cb.equal(root.get("role"), 2));
        cq.groupBy(root.get("uuid"));
        cq.orderBy(cb.asc(cb.count(chatJoin)));

        return em.createQuery(cq).getResultList();
    }
}
