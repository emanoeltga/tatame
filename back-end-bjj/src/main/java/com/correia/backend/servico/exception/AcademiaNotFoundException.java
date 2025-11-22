package com.correia.backend.servico.exception;

public class AcademiaNotFoundException extends RuntimeException {
    public AcademiaNotFoundException(Long id) {
        super("Academia não encontrada: " + id);
    }
}
