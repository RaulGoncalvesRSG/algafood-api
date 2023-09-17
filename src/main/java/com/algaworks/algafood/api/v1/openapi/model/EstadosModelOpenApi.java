package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.EstadoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "EstadosModel")
@Data
public class EstadosModelOpenApi {

	private EstadosEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "EstadosEmbeddedModel")
	@Data
	public class EstadosEmbeddedModelOpenApi {

		private List<EstadoDTO> estados;

	}

}
