package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.dto.response.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTOAssembler extends ObjectRepresentationDTOGenericConverter<PedidoDTO, Pedido, PedidoController> {

    @Autowired
    private AlgaLinks algaLinks;

    private static final String PEDIDOS = "pedidos";
    private static final String PRODUTOS = "produtos";

    public PedidoDTOAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO dto = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, dto);

        dto.add(algaLinks.linkToPedidos(PEDIDOS));
        dto.getRestaurante().add(algaLinks.linkToRestaurante(pedido.getRestaurante().getId()));
        dto.getCliente().add(algaLinks.linkToUsuario(pedido.getCliente().getId()));
        dto.getFormaPagamento().add(algaLinks.linkToFormaPagamento(pedido.getFormaPagamento().getId()));
        dto.getEnderecoEntrega().getCidade().add( algaLinks.linkToCidade(pedido.getEnderecoEntrega().getCidade().getId()));

        dto.getItens().forEach(item -> {
            item.add(algaLinks.linkToProduto(
                    pedido.getRestaurante().getId(), item.getProdutoId(), PRODUTOS));
        });

        return dto;
    }
}
