package com.algaworks.algafood.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Builder
@Data
public class CozinhaRequestDTO {

    @NotBlank
    private String nome;
}
