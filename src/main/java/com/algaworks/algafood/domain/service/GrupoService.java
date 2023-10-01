package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeEmUsoException;
import com.algaworks.algafood.domain.exception.GrupoNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.GrupoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
public class GrupoService {

    private final GrupoRepository repository;

    private static final String MSG_GRUPO_EM_USO = "Grupo de código %d não pode ser removido, pois está em uso";

    public List<Grupo> listar(){
        return repository.findAll();
    }

    @Transactional
    public Grupo salvar(Grupo grupo){
        return repository.save(grupo);
    }

    @Transactional
    public void excluir(Long id){
        try {
            repository.deleteById(id);
            repository.flush();
        } catch (EmptyResultDataAccessException e) {
            throw new GrupoNaoEncontradoException(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntidadeEmUsoException(String.format(MSG_GRUPO_EM_USO, id));
        }
    }

    public Grupo buscarOuFalhar(Long id){
        return repository
                .findById(id)
                .orElseThrow(() -> new GrupoNaoEncontradoException(id));
    }

    @Transactional
    public void associarPermissao(Grupo grupo, Permissao permissao) {
        grupo.adicionarPermissao(permissao);
    }

    @Transactional
    public void desassociarPermissao(Grupo grupo, Permissao permissao) {
        grupo.removerPermissao(permissao);
    }
}
