package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioComSenhaResquestDTO extends UsuarioRequestDTO {

    @ApiModelProperty(example = "123", required = true)
    @NotBlank
    private String senha;
}
