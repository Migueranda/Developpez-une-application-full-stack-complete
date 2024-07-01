package com.openclassrooms.mddapi.repositories;

import com.openclassrooms.mddapi.model.SubscriptionId;
import com.openclassrooms.mddapi.model.entities.Subscription;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SubscriptionRepository extends JpaRepository<Subscription, SubscriptionId> {
    List<Subscription> findByUserId(Long userId);
    List<Subscription> findBySubjectId(Long subjectId);
}


