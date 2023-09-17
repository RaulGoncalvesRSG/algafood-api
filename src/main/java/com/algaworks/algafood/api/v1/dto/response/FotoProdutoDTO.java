package com.algaworks.algafood.api.v1.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FotoProdutoDTO {

    @Schema(example = "b8bbd21a-4dd3-4954-835c-3493af2ba6a0_Prime-Rib.jpg")
    private String nomeArquivo;

    @Schema(example = "Prime Rib ao ponto")
    private String descricao;

    @Schema(example = "image/jpeg")
    private String contentType;

    @Schema(example = "202912")
    private Long tamanho;
}
