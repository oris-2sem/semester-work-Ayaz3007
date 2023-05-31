package com.example.ludikgames.repository.impl;

import com.example.ludikgames.model.SupportChat;
import com.example.ludikgames.model.User;
import com.example.ludikgames.repository.SupportChatCustomRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@RequiredArgsConstructor
public class SupportChatRepositoryImpl implements SupportChatCustomRepository {

    @PersistenceContext
    private final EntityManager em;

    @Override
    public List<SupportChat> findChatByUser(User user) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<SupportChat> cq = cb.createQuery(SupportChat.class);

        Root<SupportChat> root = cq.from(SupportChat.class);

        cq.select(root).where(cb.equal(root.get("user"), user));

        return em.createQuery(cq).getResultList();
    }
}
