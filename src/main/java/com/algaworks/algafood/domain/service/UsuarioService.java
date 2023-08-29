package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.UsuarioNaoEncontradoException;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UsuarioService {

    private final UsuarioRepository repository;
    private final BCryptPasswordEncoder passwordEncoder;

    private static final String SENHA_INVALIDA = "Senha atual informada não coincide com a senha do usuário.";
    private static final String EMAIL_EXISTENTE = "Já existe um usuário cadastrado com o e-mail %s";

    public List<Usuario> listar(){
        return repository.findAll();
    }

    @Transactional
    public Usuario salvar(Usuario usuario) {
        verificarEmailExistente(usuario);
        return repository.save(usuario);
    }

    private void verificarEmailExistente(Usuario usuario){
        //Remove o gerenciamento do JPA do obj pq ao fazer o findByEmail usando obj usuario, é feito uma sincronização dos dados, antes disso é feito um update no usuario internamente para usar o parâmetro atualizado
        repository.detach(usuario);
        Optional<Usuario> usuarioExistente = repository.findByEmail(usuario.getEmail());

        if (usuarioExistente.isPresent() && !usuarioExistente.get().equals(usuario)) {
            throw new NegocioException(String.format(EMAIL_EXISTENTE, usuario.getEmail()));
        }

        if (usuario.isNovo()){
            usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        }
    }

    @Transactional
    public void alterarSenha(Long id, String senhaAtual, String novaSenha) {
        Usuario usuario = buscarOuFalhar(id);

        if (!passwordEncoder.matches(senhaAtual, usuario.getSenha())) {
            throw new NegocioException(SENHA_INVALIDA);
        }
        usuario.setSenha(passwordEncoder.encode(novaSenha));
    }

    public Usuario buscarOuFalhar(Long usuarioId) {
        return repository
                .findById(usuarioId)
                .orElseThrow(() -> new UsuarioNaoEncontradoException(usuarioId));
    }

    @Transactional
    public void associarGrupo(Usuario usuario, Grupo grupo){
        usuario.adicionarGrupo(grupo);
    }

    @Transactional
    public void desassociarGrupo(Usuario usuario, Grupo grupo){
        usuario.removerGrupo(grupo);
    }
}
