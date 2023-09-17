package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "UsuariosModel")
@Data
public class UsuariosModelOpenApi {

	private UsuariosEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "UsuariosEmbeddedModel")
	@Data
	public class UsuariosEmbeddedModelOpenApi {

		private List<UsuarioDTO> usuarios;
	}

}
