package com.algaworks.algafood.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class CozinhaDTO {

    private Long id;
    private String nome;
}
