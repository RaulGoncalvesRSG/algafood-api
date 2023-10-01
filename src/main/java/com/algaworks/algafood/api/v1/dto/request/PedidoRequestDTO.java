package com.algaworks.algafood.api.v1.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PedidoRequestDTO {

    @Valid
    @NotNull
    private RestauranteIdRequestDTO restaurante;

    @Valid
    @NotNull
    private EnderecoRequestDTO enderecoEntrega;

    @Valid
    @NotNull
    private FormaPagamentoIdRequestDTO formaPagamento;

    @Valid
    @Size(min = 1)
    @NotNull
    private List<ItemPedidoRequestDTO> itens;
}
