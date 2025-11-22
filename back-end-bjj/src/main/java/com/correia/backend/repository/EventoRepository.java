package com.correia.backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.correia.backend.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}