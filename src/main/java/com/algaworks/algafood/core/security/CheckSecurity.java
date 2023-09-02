package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

public @interface CheckSecurity {

    @interface Cozinhas{

        @PreAuthorize("hasAuthority('EDITAR_COZINHAS')")
        @Retention(RetentionPolicy.RUNTIME)     //A anotação é armazenada no local onde é usado após a compilação para ela ser lida em tempo de execução
        @Target(ElementType.METHOD)             //Target permitindo usar a anotação apenas em métodos
        @interface PodeEditar {}

        @PreAuthorize("isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }
}
