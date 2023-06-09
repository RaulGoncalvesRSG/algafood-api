package com.algaworks.algafood.api.dto.response;

import com.algaworks.algafood.api.dto.view.RestaruanteView;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CozinhaDTO {

    @JsonView(RestaruanteView.Resumo.class)     //Retorna apenas os canpos com a anotação
    private Long id;

    @JsonView(RestaruanteView.Resumo.class)
    private String nome;
}
