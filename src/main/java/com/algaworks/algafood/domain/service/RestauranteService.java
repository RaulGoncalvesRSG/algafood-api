package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class RestauranteService {

    private final RestauranteRepository repository;
    private final CozinhaService cozinhaService;

    public List<Restaurante> listar(){
        return repository.findAll();
    }

    @Transactional
    public Restaurante salvar(Restaurante restaurante) {
        Long cozinhaId = restaurante.getCozinha().getId();
        Cozinha cozinha = cozinhaService.buscarOuFalhar(cozinhaId);
        restaurante.setCozinha(cozinha);
        return repository.save(restaurante);
    }

    public Restaurante buscarOuFalhar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }
}
