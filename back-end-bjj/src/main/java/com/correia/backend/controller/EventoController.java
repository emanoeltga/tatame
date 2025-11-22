package com.correia.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.correia.backend.controller.dto.EventoDTO;
import com.correia.backend.model.Evento;
import com.correia.backend.servico.EventoService;

@RestController
@RequestMapping("/api/eventos")
public class EventoController {

	@Autowired
    private EventoService eventoService;

    @GetMapping
    public List<Evento> listarEventos() {
        return eventoService.listarEventos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Evento> consultarEvento(@PathVariable Long id) {
        Evento evento = eventoService.consultarEvento(id);
        return ResponseEntity.ok(evento);
    }

    @PostMapping
    public Evento criarEvento(@RequestBody EventoDTO eventoDTO) {
        return eventoService.criarEvento(eventoDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Evento> editarEvento(@PathVariable Long id, @RequestBody EventoDTO eventoDTO) {
        Evento evento = eventoService.editarEvento(id, eventoDTO);
        return ResponseEntity.ok(evento);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removerEvento(@PathVariable Long id) {
        eventoService.removerEvento(id);
        return ResponseEntity.ok().build();
    }
}
