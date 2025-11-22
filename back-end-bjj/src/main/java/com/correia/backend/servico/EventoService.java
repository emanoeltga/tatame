package com.correia.backend.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.correia.backend.controller.dto.EventoDTO;
import com.correia.backend.model.Evento;
import com.correia.backend.repository.EventoRepository;
import com.correia.backend.servico.exception.EventoNotFoundException;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    public List<Evento> listarEventos() {
        return eventoRepository.findAll();
    }

    public Evento consultarEvento(Long id) {
        return eventoRepository.findById(id).orElseThrow(() -> new EventoNotFoundException(id));
    }

    public Evento criarEvento(EventoDTO eventoDTO) {
        Evento evento = new Evento();
        evento.setDescricao(eventoDTO.descricao());
        evento.setCidade(eventoDTO.cidade());
        evento.setEstado(eventoDTO.estado());
        evento.setEsporte(eventoDTO.esporte());
        evento.setData(eventoDTO.data());
        return eventoRepository.save(evento);
    }

    public Evento editarEvento(Long id, EventoDTO eventoDTO) {
        Evento evento = consultarEvento(id);
        evento.setDescricao(eventoDTO.descricao());
        evento.setCidade(eventoDTO.cidade());
        evento.setEstado(eventoDTO.estado());
        evento.setEsporte(eventoDTO.esporte());
        evento.setData(eventoDTO.data());
        return eventoRepository.save(evento);
    }

    public void removerEvento(Long id) {
        eventoRepository.deleteById(id);
    }
}
