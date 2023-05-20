package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CozinhaService {

    private final CozinhaRepository repository;

    private static final String MSG_COZINHA_EM_USO = "Cozinha de código %d não pode ser removida, pois está em uso";

    public List<Cozinha> listar(){
        return repository.findAll();
    }

    @Transactional
    public Cozinha salvar(Cozinha cozinha) {
        return repository.save(cozinha);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();         //Descarrega tds mudanças pendentes no BD, operações ainda n executadas
        } catch (EmptyResultDataAccessException e) {
            throw new CozinhaNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_COZINHA_EM_USO, id));
        }
    }

    public Cozinha buscarOuFalhar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new CozinhaNaoEncontradaException(id));
    }
}
