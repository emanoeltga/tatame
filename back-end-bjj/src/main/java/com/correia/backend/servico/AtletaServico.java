package com.correia.backend.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.correia.backend.controller.dto.AtletaDTO;
import com.correia.backend.model.Atleta;
import com.correia.backend.repository.AtletaRepository;

@Service
public class AtletaServico {

	@Autowired
	private AtletaRepository atletaRepository;
	
	public List<AtletaDTO> getTodos(){ 
				
		return atletaRepository.findAll().stream()
				.map(atleta-> atleta.converter(atleta))
				.collect(Collectors.toCollection(ArrayList::new));
	}
	public Atleta save(Atleta atleta) {
		return atletaRepository.saveAndFlush(atleta);
	}
	public AtletaDTO delete(long id) {
		Atleta atleta=new Atleta();//evita null point
		
		if (atletaRepository.existsById(id)) {
			atleta = atletaRepository.findById(id).get();
			atletaRepository.deleteById(id);
		} 	
		return atleta.converter(atleta);
	}
	public AtletaDTO buscarPorID(long id) {
		Atleta atleta=new Atleta();//evita null point		
		if (atletaRepository.existsById(id)) {
			atleta = atletaRepository.findById(id).get();			
		} 	
		return atleta.converter(atleta);
	}
	public List<AtletaDTO> buscarPorNome(String nome) {
		
		return atletaRepository.findByNomeContaining(nome).stream()
		.map(atleta-> atleta.converter(atleta))
		.collect(Collectors.toCollection(ArrayList::new));	
	

	}
}
