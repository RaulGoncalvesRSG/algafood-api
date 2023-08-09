package com.algaworks.algafood.api.openapi.model;

import com.algaworks.algafood.api.dto.response.CidadeDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@ApiModel("CidadesModel")		//Aula 19.40. Classe de modelo para modelar a documentação
public class CidadesModelOpenApi {

	private CidadesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Data
	@ApiModel("CidadesEmbeddedModel")
	public class CidadesEmbeddedModelOpenApi {

		private List<CidadeDTO> cidades;
	}

}
