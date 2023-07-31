package com.algaworks.algafood.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Getter;

import java.time.OffsetDateTime;
import java.util.List;

@ApiModel("Problema")
@JsonInclude(Include.NON_NULL)
@Getter
@Builder
public class Problem {

	@ApiModelProperty(example = "400", position = 1)
	private Integer status;

	@ApiModelProperty(example = "2019-12-01T18:09:02.70844Z", position = 2)
	private OffsetDateTime timestamp;

	@ApiModelProperty(example = "https://algafood.com.br/dados-invalidos", position = 3)
	private String type;

	@ApiModelProperty(example = "Dados inválidos", position = 4)
	private String title;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 5)
	private String detail;

	@ApiModelProperty(example = "Um ou mais campos estão inválidos. Faça o preenchimento correto e tente novamente.", position = 6)
	private String userMessage;

	@ApiModelProperty(value = "Lista de objetos ou campos que geraram o erro (opcional)", position = 7)
	private List<Field> fields;

	@ApiModel("ObjetoProblema")
	@Getter
	@Builder
	public static class Field {

		@ApiModelProperty(example = "preco", position = 1)
		private String name;

		@ApiModelProperty(example = "O preço é obrigatório", position = 1)
		private String userMessage;
	}
}
