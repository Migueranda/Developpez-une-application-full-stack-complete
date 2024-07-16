package com.openclassrooms.mddapi.controllers;

import com.openclassrooms.mddapi.services.SubscriptionService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SubscriptionController {

    @Autowired
    private SubscriptionService subscriptionService;

    @PostMapping("/subscriptions/{userId}/{subjectId}")
    public ResponseEntity<?> subscribe(@PathVariable Long userId, @PathVariable  Long subjectId) {
        subscriptionService.subscribeUserToSubject(userId, subjectId);
        return ResponseEntity.ok().body("{\"message\": \"User successfully subscribed to subject.\"}");
    }

    @DeleteMapping("/subscriptions/{userId}/{subjectId}")
    public ResponseEntity<?> unsubscribe(@PathVariable  Long userId, @PathVariable  Long subjectId) {
        subscriptionService.unsubscribeUserFromSubject(userId, subjectId);
        return ResponseEntity.ok().body("{\"message\": \"User successfully unsubscribed from subject.\"}");
    }
}
