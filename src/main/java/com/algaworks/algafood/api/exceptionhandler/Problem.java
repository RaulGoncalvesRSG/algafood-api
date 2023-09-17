package com.algaworks.algafood.api.exceptionhandler;

import com.algaworks.algafood.domain.util.Constants;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@Schema(name = Constants.SCHEMA_PROBLEMA)
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@Schema(example = "400", required = true)
	private Integer status;

	@Schema(example = "2023-08-15T18:09:02.70844Z")
	private OffsetDateTime timestamp;

	@Schema(example = "https://algafood.com.br/dados-invalidos")
	private String type;

	@Schema(example = "Dados inválidos")
	private String title;

	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String detail;

	@Schema(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.")
	private String userMessage;

	@Schema(description = "Lista de objetos ou campos que geraram o erro (opcional)")
	private List<Field> fields;

	@Schema(name = Constants.SCHEMA_OBJETO_PROBLEMA)
	@Getter
	@Builder
	public static class Field {

		@Schema(example = "Preço")
		private String name;

		@Schema(example = "O preço é inválido")
		private String userMessage;
	}
}
