package com.correia.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.correia.backend.model.Atleta;

@Repository
public interface AtletaRepository extends JpaRepository<Atleta, Long> {

	List<Atleta> findByNomeContaining(String name);
	public Atleta findByNome(String nome);
}
