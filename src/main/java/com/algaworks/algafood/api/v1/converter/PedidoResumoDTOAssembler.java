package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.response.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoResumoDTOAssembler extends ObjectRepresentationDTOGenericConverter<PedidoResumoDTO, Pedido, PedidoController> {

    @Autowired
    private AlgaLinks algaLinks;

    private static final String PEDIDOS = "pedidos";

    public PedidoResumoDTOAssembler() {
        super(PedidoController.class, PedidoResumoDTO.class);
    }

    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
        PedidoResumoDTO dto = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, dto);

        dto.add(algaLinks.linkToPedidos(PEDIDOS));
        dto.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        dto.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));

        return dto;
    }
}
