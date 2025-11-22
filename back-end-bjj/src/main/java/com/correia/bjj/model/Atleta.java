package com.correia.bjj.model;

import com.correia.bjj.controller.dto.AtletaDTO;
import com.correia.bjj.controller.dto.CategoriaDTO;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Atleta {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long atleta_id;
	private String nome;
	private String cpf;
	@ManyToOne
	@JoinColumn(name = "categoria_id")
	private Categoria categoria;
	private Double peso;
	private Long idade;
	
	public AtletaDTO converter(Atleta atleta) {		
		if (atleta != null) {
			return new AtletaDTO(
					atleta.atleta_id,
					atleta.getNome(),
					atleta.getCpf(),
					atleta.getPeso(),
					atleta.getCategoria().converter(atleta.getCategoria()),
					atleta.getIdade());
		} else return new AtletaDTO();
		
	}
	public CategoriaDTO converteCategoria(Categoria categoria) {
		if (categoria!=null) {
			return categoria.converter(categoria);
		} else 
		return new Categoria().converter(new Categoria());
	}
	
	public Categoria converteCategoriaDTO(CategoriaDTO categoria) {
		if (categoria!=null) {
			return new Categoria(categoria.getId(),categoria.getNome());
		}
		
		return new Categoria();
	}
}
