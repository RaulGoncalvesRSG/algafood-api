package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.FormaPagamentoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "FormasPagamentoModel")
@Data
public class FormasPagamentoModelOpenApi {

	private FormasPagamentoEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "FormasPagamentoEmbeddedModel")
	@Data
	public class FormasPagamentoEmbeddedModelOpenApi {

		private List<FormaPagamentoDTO> formasPagamento;

	}

}
