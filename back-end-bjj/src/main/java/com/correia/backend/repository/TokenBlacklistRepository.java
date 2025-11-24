package com.correia.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.correia.backend.model.TokenBlacklist;

public interface TokenBlacklistRepository extends JpaRepository<TokenBlacklist, Long> {

    boolean existsByToken(String token);
}
