package com.correia.backend.servico;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.correia.backend.controller.dto.CategoriaDTO;
import com.correia.backend.repository.CategoriaRepository;

@Service
public class CategoriaService {

	@Autowired
	private CategoriaRepository categoriaRepository;
	
	public List<CategoriaDTO> getTodos(){ 		
		return categoriaRepository.findAll().stream()
				.map(categoria-> categoria.converter(categoria))
				.collect(Collectors.toCollection(ArrayList::new));
	}
}
