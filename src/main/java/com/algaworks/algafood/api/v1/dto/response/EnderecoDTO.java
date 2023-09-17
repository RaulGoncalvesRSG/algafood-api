package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EnderecoDTO {

    @Schema(example = "38400-000")
    private String cep;

    @Schema(example = "Rua Floriano Peixoto")
    private String logradouro;

    @Schema(example = "1500")
    private String numero;

    @Schema(example = "Apto 901")
    private String complemento;

    @Schema(example = "Centro")
    private String bairro;

    private CidadeResumoDTO cidade;
}
