package com.correia.backend.servico;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.correia.backend.controller.dto.AcademiaDTO;
import com.correia.backend.model.Academia;
import com.correia.backend.repository.AcademiaRepository;
import com.correia.backend.servico.exception.AcademiaNotFoundException;

@Service
public class AcademiaService {

    @Autowired
    private AcademiaRepository academiaRepository;

    public List<Academia> listarAcademias() {
        return academiaRepository.findAll();
    }

    public Academia consultarAcademia(Long id) {
        return academiaRepository.findById(id).orElseThrow(() -> new AcademiaNotFoundException(id));
    }

    public Academia criarAcademia(AcademiaDTO academiaDTO) {
    	System.out.println(academiaDTO);
    	Academia academia = new Academia();
        academia.setNome(academiaDTO.nome());
        academia.setPhone(academiaDTO.phone());
        academia.setResponsavel(academiaDTO.responsavel());
        academia.setCidade(academiaDTO.cidade());
        academia.setEstado(academiaDTO.estado());
        return academiaRepository.save(academia);
    }

    public Academia editarAcademia(Long id, AcademiaDTO academiaDTO) {
        System.out.println(academiaDTO);
    	Academia academia = consultarAcademia(id);
        academia.setNome(academiaDTO.nome());
        academia.setPhone(academiaDTO.phone());
        academia.setResponsavel(academiaDTO.responsavel());
        academia.setCidade(academiaDTO.cidade());
        academia.setEstado(academiaDTO.estado());
        return academiaRepository.save(academia);
    }

    public Academia removerAcademia(Long id) {
    	Academia retorno =consultarAcademia(id); 
        academiaRepository.deleteById(id);
        return retorno;
        
    }
}