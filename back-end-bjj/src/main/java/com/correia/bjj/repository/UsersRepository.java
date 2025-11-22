package com.correia.bjj.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.correia.bjj.model.Users;

public interface UsersRepository extends JpaRepository<Users, Long> {

	Optional<Users> findByName(String name);
	Optional<Users> findByEmail(String email);
}
