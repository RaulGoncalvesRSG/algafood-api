package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos/{id}")
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    public void confirmar(@PathVariable Long id){
        fluxoPedidoService.confirmar(id);
    }

    @PutMapping("/cancelamento")
    public void cancelar(@PathVariable Long id){
        fluxoPedidoService.cancelar(id);
    }

    @PutMapping("/entrega")
    public void entregar(@PathVariable Long id){
        fluxoPedidoService.entregar(id);
    }
}
