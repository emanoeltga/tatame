package com.correia.backend.repository;


import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
//import org.springframework.data.jpa.repository.Query;
//import org.springframework.data.repository.query.Param;

import com.correia.backend.model.UserRole;

public interface RoleRepository extends JpaRepository<UserRole, Long> {

	//@Query(value = "select r from UserRole r left join r.user u  where u.user_id=:id")
	//public Optional<Collection<UserRole>> findByUser(@Param(value = "id") Long id);
	
	Optional<UserRole> findByAuthority(String authority);
}
