package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.PedidoDTOAssembler;
import com.algaworks.algafood.api.converter.PedidoResumoDTOAssembler;
import com.algaworks.algafood.api.dto.response.PedidoDTO;
import com.algaworks.algafood.api.dto.response.PedidoResumoDTO;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.service.EmissaoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private final PedidoRepository pedidoRepository;
    private final EmissaoPedidoService emissaoPedido;
    private final PedidoDTOAssembler pedidoDTOAssembler;
    private final PedidoResumoDTOAssembler pedidoResumoDTOAssembler;

    @GetMapping
    public List<PedidoResumoDTO> listar() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return pedidoResumoDTOAssembler.toCollectionDTO(pedidos);
    }

    @GetMapping("/{id}")
    public PedidoDTO buscar(@PathVariable Long id) {
        Pedido pedido = emissaoPedido.buscarOuFalhar(id);
        return pedidoDTOAssembler.toDTO(pedido);
    }
}
