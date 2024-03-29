package com.algaworks.algafood.api.v1.dto.response;

import com.algaworks.algafood.api.v1.dto.view.RestaruanteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cozinhas")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CozinhaDTO extends RepresentationModel<CozinhaDTO> {

    @Schema(example = "1")
    @JsonView(RestaruanteView.Resumo.class)     //Retorna apenas os canpos com a anotação
    private Long id;

    @Schema(example = "Brasileira")
    @JsonView(RestaruanteView.Resumo.class)
    private String nome;
}
