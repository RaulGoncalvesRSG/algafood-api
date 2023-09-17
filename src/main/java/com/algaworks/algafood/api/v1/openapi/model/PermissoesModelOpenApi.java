package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.PermissaoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "PermissoesModel")
@Data
public class PermissoesModelOpenApi {

	private PermissoesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "PermissoesEmbeddedModel")
	@Data
	public class PermissoesEmbeddedModelOpenApi {

		private List<PermissaoDTO> permissoes;

	}

}
