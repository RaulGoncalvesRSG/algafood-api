package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GrupoRequestDTO {

    @ApiModelProperty(example = "Gerente", required = true)
    @NotBlank
    private String nome;
}
