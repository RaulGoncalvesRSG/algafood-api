package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.CidadeDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Data
@Schema(description = "CidadesModel")		//Aula 19.40. Classe de modelo para modelar a documentação
public class CidadesModelOpenApi {

	private CidadesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Data
	@Schema(description = "CidadesEmbeddedModel")
	public class CidadesEmbeddedModelOpenApi {

		private List<CidadeDTO> cidades;
	}

}
