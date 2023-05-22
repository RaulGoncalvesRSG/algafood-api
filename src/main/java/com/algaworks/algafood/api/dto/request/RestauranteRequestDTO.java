package com.algaworks.algafood.api.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestauranteRequestDTO {

    @NotBlank       //Não pode ser null (NotNull), vazio ou conter apenas espaço em branco (NotEmpty)
    private String nome;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid      //Indica que será validado as propriedades do obj Cozinha (vai procurar todas validações do obj)
    @NotNull
    private CozinhaIdRequestDTO cozinha;

    @Valid
    @NotNull
    private EnderecoRequestDTO endereco;
}
