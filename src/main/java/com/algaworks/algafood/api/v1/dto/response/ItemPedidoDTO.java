package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ItemPedidoDTO extends RepresentationModel<ItemPedidoDTO> {

    @Schema(example = "1")
    private Long produtoId;

    @Schema(example = "Porco com molho agridoce")
    private String produtoNome;

    @Schema(example = "2")
    private Integer quantidade;

    @Schema(example = "78.90")
    private BigDecimal precoUnitario;

    @Schema(example = "157.80")
    private BigDecimal precoTotal;

    @Schema(example = "Menos picante, por favor")
    private String observacao;
}
