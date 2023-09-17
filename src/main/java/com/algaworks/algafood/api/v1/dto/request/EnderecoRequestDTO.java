package com.algaworks.algafood.api.v1.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
public class EnderecoRequestDTO {

    @Schema(example = "38400-000")
    @NotBlank
    private String cep;

    @Schema(example = "Rua Floriano Peixoto")
    @NotBlank
    private String logradouro;

    @Schema(example = "1500")
    @NotBlank
    private String numero;

    @Schema(example = "Apto 901")
    private String complemento;

    @Schema(example = "Centro")
    @NotBlank
    private String bairro;

    @Valid
    @NotNull
    private CidadeIdRequestDTO cidade;
}
