package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.enums.StatusPedido;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;

    private final static String ALTERACAO_STATUS_INVALIDA = "Stauts do pedido %d não pode ser alterado de %s para %s";

    @Transactional
    public void confirmar(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

        validarAlteracaoStatus(pedido, StatusPedido.CONFIRMADO);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

        validarAlteracaoStatus(pedido, StatusPedido.CANCELADO);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(Long pedidoId){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(pedidoId);

        validarAlteracaoStatus(pedido, StatusPedido.ENTREGUE);
        pedido.entregar();
    }

    private void validarAlteracaoStatus(Pedido pedido, StatusPedido novoStatus){
        if (pedido.getStatus().naoPodeAlterarPara(novoStatus)){
            throw new NegocioException(String.format(ALTERACAO_STATUS_INVALIDA, pedido.getId(), pedido.getStatus().getDescricao(), novoStatus.getDescricao()));
        }
    }
}
