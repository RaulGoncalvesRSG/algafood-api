package com.algaworks.algafood.api.dto.response;

import com.algaworks.algafood.api.dto.view.RestaruanteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
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

    @ApiModelProperty(example = "1")
    @JsonView({RestaruanteView.Resumo.class, RestaruanteView.ApenasNome.class})
    private Long id;

    @ApiModelProperty(example = "Thai Gourmet")
    @JsonView({RestaruanteView.Resumo.class, RestaruanteView.ApenasNome.class})
    private String nome;

    @ApiModelProperty(example = "12.00")
    @JsonView(RestaruanteView.Resumo.class)
    private BigDecimal taxaFrete;

    @JsonView(RestaruanteView.Resumo.class)
    private CozinhaDTO cozinha;

    private Boolean ativo;
    private Boolean aberto;
    private EnderecoDTO endereco;
}
