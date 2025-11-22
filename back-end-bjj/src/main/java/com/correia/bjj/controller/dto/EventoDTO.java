package com.correia.bjj.controller.dto;

import java.time.LocalDate;

public record EventoDTO(Long id, String descricao, String cidade, String estado, String esporte, LocalDate data) {
}
