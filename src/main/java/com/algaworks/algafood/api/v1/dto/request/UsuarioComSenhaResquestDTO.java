package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(example = "123", type = "string")
    @NotBlank
    private String senha;
}
