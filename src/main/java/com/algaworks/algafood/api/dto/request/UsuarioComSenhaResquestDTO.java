package com.algaworks.algafood.api.dto.request;

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

    @NotBlank
    private String senha;
}
