package com.correia.backend.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.correia.backend.controller.dto.CategoriaDTO;
import com.correia.backend.servico.CategoriaService;

@RestController
@RequestMapping("categorias")
public class CategoriaController {

	@Autowired
	private CategoriaService categoriaService;
	@GetMapping
	public Collection<CategoriaDTO> getAll() {
		return categoriaService.getTodos();
	}
}
