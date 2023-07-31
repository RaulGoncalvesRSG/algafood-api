package com.algaworks.algafood.api.dto.request;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UsuarioRequestDTO {

    @ApiModelProperty(example = "João da Silva", required = true)
    @NotBlank
    private String nome;

    @ApiModelProperty(example = "joao.ger@algafood.com.br", required = true)
    @NotBlank
    @Email
    private String email;
}
