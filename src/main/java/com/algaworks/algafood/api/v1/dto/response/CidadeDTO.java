package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "cidades")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CidadeDTO extends RepresentationModel<CidadeDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Uberlândia")
    private String nome;

    private EstadoDTO estado;
}
