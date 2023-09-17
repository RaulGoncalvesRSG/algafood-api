package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoDTO {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Espetinho de Cupim")
    private String nome;

    @Schema(example = "Acompanha farinha, mandioca e vinagrete")
    private String descricao;

    @Schema(example = "12.50")
    private BigDecimal preco;

    @Schema(example = "true")
    private Boolean ativo;
}
