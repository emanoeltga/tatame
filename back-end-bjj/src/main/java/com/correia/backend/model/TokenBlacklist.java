package com.correia.backend.model;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@Entity
public class TokenBlacklist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 800)
    private String token;

    private Instant expiration;

    public TokenBlacklist() {}

    public TokenBlacklist(String token, Instant expiration) {
        this.token = token;
        this.expiration = expiration;
    }

    // getters e setters
}
