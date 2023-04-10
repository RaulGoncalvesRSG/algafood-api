package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstadoService {

    private final EstadoRepository repository;

    public List<Estado> listar(){
        return repository.listar();
    }

    public Estado buscar(Long id){
        Estado estado = repository.buscar(id);

        if (estado == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de estado com código %d.", id));
        }
        return estado;
    }

    public Estado salvar(Estado Estado) {
        return repository.salvar(Estado);
    }

    public void excluir(Long id) {
        //chamar buscar

        try {
            repository.remover(id);
        } catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de estado com código %d.", id));
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Estado de código %d não pode ser removida, pois está em uso.", id));
        }
    }
}
