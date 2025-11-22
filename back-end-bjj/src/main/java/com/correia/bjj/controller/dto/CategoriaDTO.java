package com.correia.bjj.controller.dto;

import com.correia.bjj.model.Categoria;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CategoriaDTO {

	private Long id;
	private String nome;	
	
	public Categoria converter(CategoriaDTO categoria) {
		Categoria retorno= new Categoria();
		retorno.setCategoria_id(categoria.getId());
		retorno.setNome(categoria.getNome());
		return retorno;
	}
}
