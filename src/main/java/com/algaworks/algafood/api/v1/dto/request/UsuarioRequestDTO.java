package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "João da Silva")
    @NotBlank
    private String nome;

    @Schema(example = "joao.ger@algafood.com.br")
    @NotBlank
    @Email
    private String email;
}
