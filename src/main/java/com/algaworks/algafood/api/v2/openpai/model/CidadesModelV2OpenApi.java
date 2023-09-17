package com.algaworks.algafood.api.v2.openpai.model;

import com.algaworks.algafood.api.v2.dto.response.CidadeDTOV2;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "CidadesModel")
@Data
public class CidadesModelV2OpenApi {

	private CidadesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "CidadesEmbeddedModel")
	@Data
	public class CidadesEmbeddedModelOpenApi {
		
		private List<CidadeDTOV2> cidades;
	}
	
}
