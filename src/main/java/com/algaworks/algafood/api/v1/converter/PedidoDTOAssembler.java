package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.response.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PedidoDTOAssembler extends ObjectRepresentationDTOGenericConverter<PedidoDTO, Pedido, PedidoController> {

    @Autowired
    private AlgaLinks algaLinks;

    private static final String PEDIDOS = "pedidos";
    private static final String PRODUTOS = "produtos";
    private static final String CONFIRMAR = "confirmar";
    private static final String CANCELAR = "cancelar";
    private static final String ENTREGAR = "entregar";

    public PedidoDTOAssembler() {
        super(PedidoController.class, PedidoDTO.class);
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO dto = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, dto);

        dto.add(algaLinks.linkToPedidos(PEDIDOS));

        if (pedido.podeSerConfirmado()) {
            dto.add(algaLinks.linkToConfirmacaoPedido(pedido.getCodigo(), CONFIRMAR));
        }

        if (pedido.podeSerCancelado()) {
            dto.add(algaLinks.linkToCancelamentoPedido(pedido.getCodigo(), CANCELAR));
        }

        if (pedido.podeSerEntregue()) {
            dto.add(algaLinks.linkToEntregaPedido(pedido.getCodigo(), ENTREGAR));
        }

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
