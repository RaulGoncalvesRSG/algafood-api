package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequestDTO {

    @ApiModelProperty(example = "Espetinho de Cupim", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "Acompanha farinha, mandioca e vinagrete", required = true)
    @NotBlank
    private String descricao;

    @ApiModelProperty(example = "12.50", required = true)
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @ApiModelProperty(example = "true", required = true)
    @NotNull
    private Boolean ativo;
}
