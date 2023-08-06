package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.dto.response.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Component
public class PedidoResumoDTOAssembler extends ObjectRepresentationDTOGenericConverter<PedidoResumoDTO, Pedido, PedidoController> {

    private final Class<PedidoController> controllerClass;
    private static final String PEDIDOS = "pedidos";

    public PedidoResumoDTOAssembler() {
        super(PedidoController.class, PedidoResumoDTO.class);
        this.controllerClass = PedidoController.class;
    }


    @Override
    public PedidoResumoDTO toModel(Pedido pedido) {
        PedidoResumoDTO dto = createModelWithId(pedido.getId(), pedido);
        modelMapper.map(pedido, dto);

        dto.add(linkTo(controllerClass)
                .withRel(PEDIDOS));

        dto.getRestaurante().add(
                linkTo(methodOn(RestauranteController.class)
                        .buscar(pedido.getRestaurante().getId())
                ).withSelfRel());

        dto.getCliente().add(
                linkTo(methodOn(UsuarioController.class)
                        .buscar(pedido.getCliente().getId())
                ).withSelfRel());

        return dto;
    }
}
