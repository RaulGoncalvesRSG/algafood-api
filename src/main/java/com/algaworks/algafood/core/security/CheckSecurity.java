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

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_COZINHAS')")         //SCOPE_WRITE - Informação de escopo do token JWT
        @Retention(RetentionPolicy.RUNTIME)     //A anotação é armazenada no local onde é usado após a compilação para ela ser lida em tempo de execução
        @Target(ElementType.METHOD)             //Target permitindo usar a anotação apenas em métodos
        @interface PodeEditar {}

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

     @interface Restaurantes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_RESTAURANTES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarCadastro { }

         //O responsável pelo seu restaurante n pode editar os dados do seu próprio restaurante
         //O @ no @algaSecurity dá acesso a instância de um Bean, então consegue fazer a chamada de algum método. O padrão do bean é inicial minúsculo
         @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                 + "(hasAuthority('EDITAR_RESTAURANTES') or "
                 + "@algaSecurity.gerenciaRestaurante(#restauranteId))")
         @Retention(RUNTIME)
         @Target(METHOD)
         @interface PodeGerenciarFuncionamento { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }
}
