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

    @Transactional
    public void ativar(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        /*N precisa usar o epository.save pq qnd busca o Restaurante com repository.findById, a instância do Restaurante fica em um estado gerenciável pelo contexto de persistência do JPA
        Qualquer modificação feita nesse obj dentro dessa transação será sincronizada (fazer update) com BD, o JPA conseguen entender isso*/
        restaurante.ativar();
    }

    @Transactional
    public void inativar(Long restauranteId){
        Restaurante restaurante = buscarOuFalhar(restauranteId);
        restaurante.inativar();
    }

    public Restaurante buscarOuFalhar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new RestauranteNaoEncontradoException(id));
    }
}
