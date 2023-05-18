package com.algaworks.algafood.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class EstadoDTO {

    private Long id;
    private String nome;
}
