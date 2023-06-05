package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.PedidoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Pedido;
import com.algaworks.algafood.domain.repository.PedidoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EmissaoPedidoService {

    private final PedidoRepository repository;

    public List<Pedido> listar(){
        return repository.findAll();
    }

    public Pedido buscarOuFalhar(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new PedidoNaoEncontradoException(id));
    }
}
