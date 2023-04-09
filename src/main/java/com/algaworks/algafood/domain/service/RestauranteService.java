package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    private final RestauranteRepository repository;
    private final CozinhaRepository cozinhaRepository;

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

    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaRepository.buscar(cozinhaId);

        if (cozinha == null){
            throw new EntidadeNaoEncontradaException(String.format("Não existe um cadastro de cozinha com código %d.", cozinhaId));
        }

        restaurante.setCozinha(cozinha);
        return repository.salvar(restaurante);
    }
}
