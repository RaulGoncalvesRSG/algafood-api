package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.ProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ProdutoService {

    private final ProdutoRepository repository;

    public List<Produto> findTodosByRestaurante(Restaurante restaurante){
        return repository.findTodosByRestaurante(restaurante);
    }

    public List<Produto> findAtivosByRestaurante(Restaurante restaurante){
        return repository.findAtivosByRestaurante(restaurante);
    }

    @Transactional
    public Produto salvar(Produto produto) {
        return repository.save(produto);
    }

    public Produto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return repository.findById(restauranteId, produtoId)
                .orElseThrow(() -> new ProdutoNaoEncontradoException(restauranteId, produtoId));
    }
}
