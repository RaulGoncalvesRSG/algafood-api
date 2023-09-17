package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.CozinhaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "CozinhasModel")
@Setter
@Getter
public class CozinhasModelOpenApi {

	private CozinhasEmbeddedModelOpenApi _embedded;
	private Links _links;
	private PagedModelOpenApi page;			//Paginação

	@Data
	@Schema(description = "CozinhasEmbeddedModel")
	public class CozinhasEmbeddedModelOpenApi {

		private List<CozinhaDTO> cozinhas;
	}

}
