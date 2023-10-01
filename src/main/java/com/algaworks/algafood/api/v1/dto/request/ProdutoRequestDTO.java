package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProdutoRequestDTO {

    @Schema(example = "Espetinho de carne")
    @NotBlank
    private String nome;

    @Schema(example = "Acompanha farinha, mandioca e vinagrete")
    @NotBlank
    private String descricao;

    @Schema(example = "12.50")
    @NotNull
    @PositiveOrZero
    private BigDecimal preco;

    @Schema(example = "true")
    @NotNull
    private Boolean ativo;
}
