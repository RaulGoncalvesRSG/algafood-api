package com.algaworks.algafood.domain.event;

import com.algaworks.algafood.domain.model.Pedido;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PedidoConfirmadoEvent {   //É uma boa prática o nome ser no passado, pois qnd dispara um evento, está falando sobre algo q aconteceu

    private Pedido pedido;
}
