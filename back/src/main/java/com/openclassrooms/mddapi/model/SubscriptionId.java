package com.openclassrooms.mddapi.model;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter
public class SubscriptionId implements Serializable {
    private Long user; // Correspond au type de l'ID de UserEntity
    private Long subject; // Correspond au type de l'ID de Subject

    public SubscriptionId() {
    }

    public SubscriptionId(Long user, Long subject) {
        this.user = user;
        this.subject = subject;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubscriptionId that = (SubscriptionId) o;
        return Objects.equals(user, that.user) && Objects.equals(subject, that.subject);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user, subject);
    }
}

