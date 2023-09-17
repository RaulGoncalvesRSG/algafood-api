package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.hateoas.RepresentationModel;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EstadoDTO extends RepresentationModel<EstadoDTO> {

    @Schema(example = "1")
    private Long id;

    @Schema(example = "Minas Gerais")
    private String nome;
}
