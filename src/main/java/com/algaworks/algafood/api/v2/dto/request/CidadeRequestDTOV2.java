package com.algaworks.algafood.api.v2.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Schema(description = "CidadeRequestDTO")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CidadeRequestDTOV2 {

    @Schema(example = "Uberlândia")
    @NotBlank
    private String nomeCidade;

    @Schema(example = "1")
    @NotNull
    private Long idEstado;
}
