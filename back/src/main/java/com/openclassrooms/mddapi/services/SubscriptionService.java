package com.openclassrooms.mddapi.services;

import com.openclassrooms.mddapi.model.SubscriptionId;
import com.openclassrooms.mddapi.model.entities.Subject;
import com.openclassrooms.mddapi.model.entities.Subscription;
import com.openclassrooms.mddapi.model.entities.UserEntity;
import com.openclassrooms.mddapi.repositories.SubjectRepository;
import com.openclassrooms.mddapi.repositories.SubscriptionRepository;
import com.openclassrooms.mddapi.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class SubscriptionService {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    public void subscribeUserToSubject(Long userId, Long subjectId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new EntityNotFoundException("User not found with id: " + userId));
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() ->
                        new EntityNotFoundException("Subject not found with id: " + subjectId));

        SubscriptionId subscriptionId = new SubscriptionId(userId, subjectId);
        Optional<Subscription> existingSubscription = subscriptionRepository.findById(subscriptionId);
        if (existingSubscription.isPresent()) {
            throw new IllegalStateException("Subscription already exists for given user and subject");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setSubject(subject);

        try {
            subscriptionRepository.save(subscription);
        } catch (DataIntegrityViolationException e) {
            throw new IllegalStateException("Failed to create subscription due to data integrity violation", e);
        }
    }

    public void unsubscribeUserFromSubject(Long userId, Long subjectId) {
        SubscriptionId subscriptionId = new SubscriptionId();
        subscriptionId.setUser(userId);
        subscriptionId.setSubject(subjectId);
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new EntityNotFoundException("Subscription not found"));
        subscriptionRepository.delete(subscription);
    }
}
