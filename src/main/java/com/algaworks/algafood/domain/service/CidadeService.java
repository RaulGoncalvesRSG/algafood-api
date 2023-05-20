package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.CidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CidadeService {

    private final CidadeRepository repository;

    private final EstadoService estadoService;

    private static final String MSG_CIDADE_EM_USO = "Cidade de código %d não pode ser removida, pois está em uso";

    public List<Cidade> listar(){
        return repository.findAll();
    }

    @Transactional
    public Cidade salvar(Cidade cidade) {
        Long estadoId = cidade.getEstado().getId();
        Estado estado = estadoService.buscarOuFalhar(estadoId);
        cidade.setEstado(estado);
        return repository.save(cidade);
    }

    @Transactional
    public void excluir(Long id) {
        try {
            repository.deleteById(id);
            repository.flush();         //Descarrega tds mudanças pendentes no BD, operações ainda n executadas
        } catch (EmptyResultDataAccessException e) {
            throw new CidadeNaoEncontradaException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_CIDADE_EM_USO, id));
        }
    }

    public Cidade buscarOuFalhar(Long id) {
        return repository
                .findById(id)
                .orElseThrow(() -> new CidadeNaoEncontradaException(id));
    }
}
