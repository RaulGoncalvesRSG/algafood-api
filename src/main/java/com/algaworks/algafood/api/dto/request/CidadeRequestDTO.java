package com.algaworks.algafood.api.dto.request;

import lombok.Builder;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder
@Data
public class CidadeRequestDTO {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdRequestDTO estado;
}
