package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.dto.response.PedidoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Component
public class PedidoDTOAssembler extends ObjectRepresentationDTOGenericConverter<PedidoDTO, Pedido, PedidoController> {

    private final Class<PedidoController> controllerClass;
    private static final String PEDIDOS = "pedidos";
    private static final String PRODUTOS = "produtos";

    public PedidoDTOAssembler() {
        super(PedidoController.class, PedidoDTO.class);
        this.controllerClass = PedidoController.class;
    }

    @Override
    public PedidoDTO toModel(Pedido pedido) {
        PedidoDTO dto = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, dto);

        dto.add(linkTo(controllerClass)
                .withRel(PEDIDOS));

        dto.getRestaurante().add(
                linkTo(methodOn(RestauranteController.class)
                        .buscar(pedido.getRestaurante().getId())
                ).withSelfRel());

        dto.getRestaurante().add(
                linkTo(methodOn(UsuarioController.class)
                        .buscar(pedido.getCliente().getId())
                ).withSelfRel());

        dto.getFormaPagamento().add(
                linkTo(methodOn(FormaPagamentoController.class)
                    .buscar(pedido.getFormaPagamento().getId())
                ).withSelfRel());

        dto.getEnderecoEntrega().getCidade().add(
                linkTo(methodOn(CidadeController.class)
                        .buscar(pedido.getEnderecoEntrega().getCidade().getId())
                ).withSelfRel());

        dto.getItens().forEach(item -> {
            item.add(linkTo(methodOn(RestauranteProdutoController.class)
                        .buscar(pedido.getRestaurante().getId(), item.getProdutoId()))
                    .withRel(PRODUTOS));
        });

        return dto;
    }
}
