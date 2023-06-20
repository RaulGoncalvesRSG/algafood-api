package com.algaworks.algafood.api.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class FotoProdutoDTO {

    private String nomeArquivo;
    private String descricao;
    private String contentType;
    private Long tamanho;
}
