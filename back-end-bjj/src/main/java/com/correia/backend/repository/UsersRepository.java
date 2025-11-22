package com.correia.backend.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.correia.backend.model.User;

public interface UsersRepository extends JpaRepository<User, Long> {

	Optional<User> findByName(String name);
	Optional<User> findByEmail(String email);
}
