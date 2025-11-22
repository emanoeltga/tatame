package com.correia.bjj.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.correia.bjj.model.Evento;

public interface EventoRepository extends JpaRepository<Evento, Long> {
}