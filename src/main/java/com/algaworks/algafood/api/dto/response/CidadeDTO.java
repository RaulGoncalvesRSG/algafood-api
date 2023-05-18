package com.algaworks.algafood.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CidadeDTO {

    private Long id;
    private String nome;
    private EstadoDTO estado;
}
