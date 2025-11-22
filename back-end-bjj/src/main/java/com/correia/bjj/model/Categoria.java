package com.correia.bjj.model;

import java.util.Collection;

import com.correia.bjj.controller.dto.CategoriaDTO;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Categoria {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY )
	private Long categoria_id;
	private String nome;
	/*@OneToMany (mappedBy = "categoria", targetEntity=Atleta.class, cascade = CascadeType.ALL)
	private Collection<Atleta> atleta;*/
	
	public Categoria(String nome) {
		this.nome = nome;
	}
	
	public CategoriaDTO converter(Categoria categoria) {
		CategoriaDTO retorno= new CategoriaDTO();
		retorno.setId(categoria.getCategoria_id());
		retorno.setNome(categoria.getNome());
		return retorno;
	}
	
}
