package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.PedidoRequestDTO;
import com.algaworks.algafood.domain.model.Pedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PedidoRequestDTODisassembler extends ObjectDTOGenericConverter<PedidoRequestDTO, Pedido> {

}
