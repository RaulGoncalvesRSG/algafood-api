package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoDTOAssembler extends ObjectDTOGenericConverter<PedidoDTO, Pedido> {

}
