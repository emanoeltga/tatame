package com.correia.backend.servico.exception;

public class EventoNotFoundException extends RuntimeException {
    public EventoNotFoundException(Long id) {
        super("Evento não encontrado: " + id);
    }
}