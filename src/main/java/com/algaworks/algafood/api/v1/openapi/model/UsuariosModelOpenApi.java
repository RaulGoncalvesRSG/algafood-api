package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@ApiModel("UsuariosModel")
@Data
public class UsuariosModelOpenApi {

	private UsuariosEmbeddedModelOpenApi _embedded;
	private Links _links;

	@ApiModel("UsuariosEmbeddedModel")
	@Data
	public class UsuariosEmbeddedModelOpenApi {

		private List<UsuarioDTO> usuarios;

	}

}