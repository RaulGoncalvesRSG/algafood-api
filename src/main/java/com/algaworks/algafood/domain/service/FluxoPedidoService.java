package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.event.PedidoCanceladoEvent;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.model.enums.StatusPedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@RequiredArgsConstructor
@Service
public class FluxoPedidoService {

    private final EmissaoPedidoService emissaoPedidoService;
    /*O evento não será disparado msmo depois q a transação do método confirmar for concluída
    Para o evento ser disparado (particularidade do Spring Data), é preciso chamar o método save do respository (precisa ser do Spring Data)*/
    private final PedidoRepository pedidoRepository;
    private final ApplicationEventPublisher publisher;

    private final static String ALTERACAO_STATUS_INVALIDA = "Stauts do pedido %s não pode ser alterado de %s para %s";

    @Transactional
    public void confirmar(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        validarAlteracaoStatus(pedido, StatusPedido.CONFIRMADO);
        pedido.confirmar();

        //Mesmo não chamando save, o obj seria salvo, pois o método confirmar faz alterações de uma instância q está sendo gerenciadas pelo EntityManager do JPA
        pedidoRepository.save(pedido);
    }

    @Transactional
    public void cancelar(String codigo){
        Pedido pedido = emissaoPedidoService.buscarOuFalhar(codigo);
        validarAlteracaoStatus(pedido, StatusPedido.CANCELADO);
        pedido.cancelar();

        publisher.publishEvent(new PedidoCanceladoEvent(pedido));
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
