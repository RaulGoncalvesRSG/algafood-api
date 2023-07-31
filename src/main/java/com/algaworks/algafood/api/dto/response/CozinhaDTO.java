package com.algaworks.algafood.api.dto.response;

import com.algaworks.algafood.api.dto.view.RestaruanteView;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CozinhaDTO {

    @ApiModelProperty(example = "1")
    @JsonView(RestaruanteView.Resumo.class)     //Retorna apenas os canpos com a anotação
    private Long id;

    @ApiModelProperty(example = "Brasileira")
    @JsonView(RestaruanteView.Resumo.class)
    private String nome;
}
