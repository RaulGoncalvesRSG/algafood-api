package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CidadeService {

    private final CidadeRepository repository;
    private final EstadoRepository estadoRepository;

    public List<Cidade> listar(){
        return repository.listar();
    }

    public Cidade buscar(Long id){
        Cidade cidade = repository.buscar(id);

        if (cidade == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cidade com código %d.", id));
        }
        return cidade;
    }

    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoRepository.buscar(estadoId);

        if (estado == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de estado com código %d.", estadoId));
        }

        cidade.setEstado(estado);
        return repository.salvar(cidade);
    }

    public void excluir(Long id) {
        try {
            repository.remover(id);
        } catch(EmptyResultDataAccessException e){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cidade com código %d.", id));
        } catch (DataIntegrityViolationException e){
            throw new EntidadeEmUsoException(String.format("Cidade de código %d não pode ser removida, pois está em uso.", id));
        }
    }
}
