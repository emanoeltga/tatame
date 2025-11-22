package com.correia.bjj.controller.dto;

import com.correia.bjj.model.Atleta;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AtletaDTO {

	private Long id;
	private String name;
	private String cpf;
	private Double peso;
	private CategoriaDTO categoria;
	private Long idade;

	public Atleta converter(AtletaDTO atleta) {
		Atleta retorno = new Atleta();
		retorno.setAtleta_id(atleta.getId());
		retorno.setNome(atleta.getName());
		retorno.setCpf(atleta.getCpf());
		retorno.setCategoria(atleta.getCategoria().converter(atleta.getCategoria()));    //retorno.setCategoria(retorno.converteCategoriaDTO(atleta.getCategoria()));
		retorno.setPeso(atleta.getPeso());
		retorno.setIdade(atleta.getIdade());

		return retorno;
	}

}
