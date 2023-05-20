package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.EstadoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class EstadoService {

    private final EstadoRepository repository;

    private static final String MSG_ESTADO_EM_USO = "Estado de código %d não pode ser removido, pois está em uso";

    public List<Estado> listar(){
        return repository.findAll();
    }

    @Transactional
    public Estado salvar(Estado estado) {
        return repository.save(estado);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();         //Descarrega tds mudanças pendentes no BD, operações ainda n executadas
        } catch (EmptyResultDataAccessException e) {
            throw new EstadoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_ESTADO_EM_USO, id));
        }
    }

    public Estado buscarOuFalhar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new EstadoNaoEncontradoException(id));
    }
}
