package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.ProdutoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "ProdutosModel")
@Data
public class ProdutosModelOpenApi {

	private ProdutosEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "ProdutosEmbeddedModel")
	@Data
	public class ProdutosEmbeddedModelOpenApi {

		private List<ProdutoDTO> produtos;
	}

}
