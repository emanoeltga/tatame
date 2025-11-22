package com.correia.bjj.repository;

import java.util.Collection;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.correia.bjj.model.Role;

public interface RoleRepository extends JpaRepository<Role, Long> {

	@Query(value = "select r from Role r left join r.user u  where u.user_id=:id")
	public Optional<Collection<Role>> findByUser(@Param(value = "id") Long id);
}
