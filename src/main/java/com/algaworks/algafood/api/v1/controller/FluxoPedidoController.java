package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.openapi.controller.FluxoPedidoControllerOpenApi;
import com.algaworks.algafood.domain.service.FluxoPedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/pedidos/{codigo}")
public class FluxoPedidoController implements FluxoPedidoControllerOpenApi {

    private final FluxoPedidoService fluxoPedidoService;

    @PutMapping("/confirmacao")
    public ResponseEntity<Void> confirmar(@PathVariable String codigo){
        fluxoPedidoService.confirmar(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/cancelamento")
    public ResponseEntity<Void> cancelar(@PathVariable String codigo){
        fluxoPedidoService.cancelar(codigo);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/entrega")
    public ResponseEntity<Void> entregar(@PathVariable String codigo){
        fluxoPedidoService.entregar(codigo);
        return ResponseEntity.noContent().build();
    }
}
