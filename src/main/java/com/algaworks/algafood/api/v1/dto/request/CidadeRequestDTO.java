package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CidadeRequestDTO {

    @ApiModelProperty(example = "Uberlândia", required = true)
    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdRequestDTO estado;
}
