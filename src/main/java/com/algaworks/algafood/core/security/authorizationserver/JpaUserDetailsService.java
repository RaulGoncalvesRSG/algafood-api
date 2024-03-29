package com.algaworks.algafood.core.security.authorizationserver;

import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service                    //UserDetailsService faz o Spring consultar o usuário
public class JpaUserDetailsService implements UserDetailsService {

    private final UsuarioRepository usuarioRepository;
    private static final String MSG_USER_NOT_FOUND = "Usuário não encontrado com e-mail informado";

    /*O find do JPA fecha o EntityManager logo após a consulta, então ele gera erro ao buscar os grupos/permissões. O Transactional
    resolve o problema da exceção e faz com q o EntityManager eja fechado somente no final do escopo. Mantendo ele aberto,
    se torna possível buscar os grupos e permissões*/
    @Transactional(readOnly = true)     //readOnly = true - abre uma transação q n vai ter alteração de informaçãos
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(MSG_USER_NOT_FOUND));

        return new User(usuario.getEmail(), usuario.getSenha(), getAuthorities(usuario));
    }

    //Lista de persmissões do usuário logado
    private Collection<GrantedAuthority> getAuthorities(Usuario usuario) {
        return usuario.getGrupos().stream()
                //Stream de todas permissões (de todos os grupos) que o usuário possui
                .flatMap(grupo -> grupo.getPermissoes().stream())
                //SimpleGrantedAuthority é uma classe q implementa GrantedAuthority. Ela tem a String da permissão. Ex: "CONSULAR_COZINHAS"
                .map(permissao -> new SimpleGrantedAuthority(permissao.getNome().toUpperCase()))
                .collect(Collectors.toSet());
    }
}
