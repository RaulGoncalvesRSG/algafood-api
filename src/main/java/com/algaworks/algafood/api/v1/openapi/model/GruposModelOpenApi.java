package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.GrupoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "GruposModel")
@Data
public class GruposModelOpenApi {

	private GruposEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "GruposEmbeddedModel")
	@Data
	public class GruposEmbeddedModelOpenApi {

		private List<GrupoDTO> grupos;
	}

}
