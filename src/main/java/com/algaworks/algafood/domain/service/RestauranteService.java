package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    private final RestauranteRepository repository;

    public List<Restaurante> listar(){
        return repository.listar();
    }

    public Restaurante buscar(Long id){
        Restaurante restaurante = repository.buscar(id);

        if (restaurante == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de restaurante com código %d.", id));
        }
        return restaurante;
    }
}
