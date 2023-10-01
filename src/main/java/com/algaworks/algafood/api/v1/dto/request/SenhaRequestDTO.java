package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.NotBlank;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SenhaRequestDTO {

    @Schema(example = "123", type = "string")
    @NotBlank
    private String senhaAtual;

    @Schema(example = "1234", type = "string")
    @NotBlank
    private String novaSenha;
}
