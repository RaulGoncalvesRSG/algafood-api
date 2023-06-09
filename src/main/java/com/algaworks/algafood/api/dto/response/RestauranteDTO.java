package com.algaworks.algafood.api.dto.response;

import com.algaworks.algafood.api.dto.view.RestaruanteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteDTO {

    @JsonView({RestaruanteView.Resumo.class, RestaruanteView.ApenasNome.class})
    private Long id;

    @JsonView({RestaruanteView.Resumo.class, RestaruanteView.ApenasNome.class})
    private String nome;

    @JsonView(RestaruanteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestaruanteView.Resumo.class)
    private CozinhaDTO cozinha;

    private Boolean ativo;
    private EnderecoDTO endereco;
    private Boolean aberto;
}
