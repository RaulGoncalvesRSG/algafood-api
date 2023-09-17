package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.CozinhaDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Schema(description = "RestauranteBasicoModel")
@Setter
@Getter
public class RestauranteBasicoModelOpenApi {		//Classe para documentação. Representação do @JsonView

	@Schema(example = "1")
	private Long id;

	@Schema(example = "Thai Gourmet")
	private String nome;

	@Schema(example = "12.00")
	private BigDecimal taxaFrete;

	private CozinhaDTO cozinha;

}
