package com.correia.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.correia.backend.model.Academia;

@Repository
public interface AcademiaRepository extends JpaRepository<Academia, Long> {

	public Academia findByNome(String nome);
}
