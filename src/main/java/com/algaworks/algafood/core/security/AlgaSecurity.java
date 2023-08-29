package com.algaworks.algafood.core.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

@Component
public class AlgaSecurity {

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();  //Representa o obj Token da requição atual
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();         //Token JWT com todas informações autocontidas
        return jwt.getClaim("usuario_id");
    }
}
