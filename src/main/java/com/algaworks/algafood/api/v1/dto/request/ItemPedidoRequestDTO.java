package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoRequestDTO {

    @Schema(example = "1")
    @NotNull
    private Long produtoId;

    @Schema(example = "2")
    @Min(1)
    @NotNull
    private Integer quantidade;

    @Schema(example = "Menos picante, por favor")
    private String observacao;
}
