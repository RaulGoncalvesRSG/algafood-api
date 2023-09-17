package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "formasPagamento")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FormaPagamentoDTO extends RepresentationModel<FormaPagamentoDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Cartão de crédito")
    private String descricao;
}
