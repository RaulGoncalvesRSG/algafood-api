package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

@Relation(collectionRelation = "usuarios")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioDTO extends RepresentationModel<UsuarioDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "João da Silva")
    private String nome;

    @Schema(example = "joao.ger@algafood.com.br")
    private String email;
}
