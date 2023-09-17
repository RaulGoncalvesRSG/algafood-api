package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "restaurantes")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteApenasNomeDTO extends RepresentationModel<RestauranteApenasNomeDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Thai Gourmet")
    private String nome;
}
