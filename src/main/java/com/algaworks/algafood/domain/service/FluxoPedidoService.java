package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.enums.StatusPedido;
import com.algaworks.algafood.domain.service.EnvioEmailService.Mensagem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;
    private final EnvioEmailService envioEmailService;

    private final static String ALTERACAO_STATUS_INVALIDA = "Stauts do pedido %s não pode ser alterado de %s para %s";

    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        validarAlteracaoStatus(pedido, StatusPedido.CONFIRMADO);
        pedido.confirmar();

        Mensagem mensagem = Mensagem.builder().build();

        envioEmailService.enviar(mensagem);
    }

    @Transactional
    public void cancelar(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        validarAlteracaoStatus(pedido, StatusPedido.CANCELADO);
        pedido.cancelar();
    }

    @Transactional
    public void entregar(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        validarAlteracaoStatus(pedido, StatusPedido.ENTREGUE);
        pedido.entregar();
    }

    private void validarAlteracaoStatus(Pedido pedido, StatusPedido novoStatus){
        if (pedido.getStatus().naoPodeAlterarPara(novoStatus)){
            throw new NegocioException(String.format(ALTERACAO_STATUS_INVALIDA, pedido.getCodigo(), pedido.getStatus().getDescricao(), novoStatus.getDescricao()));
        }
    }
}
