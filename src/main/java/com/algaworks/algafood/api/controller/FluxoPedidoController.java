package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos/{codigo}")
public class FluxoPedidoController {

    private final FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    public void confirmar(@PathVariable String codigo){
        fluxoPedidoService.confirmar(codigo);
    }

    @PutMapping("/cancelamento")
    public void cancelar(@PathVariable String codigo){
        fluxoPedidoService.cancelar(codigo);
    }

    @PutMapping("/entrega")
    public void entregar(@PathVariable String codigo){
        fluxoPedidoService.entregar(codigo);
    }
}
