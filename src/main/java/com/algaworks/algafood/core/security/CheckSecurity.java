package com.algaworks.algafood.core.security;

import org.springframework.security.access.prepost.PostAuthorize;
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

        @PreAuthorize("hasAuthority('SCOPE_READ')")
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

         //@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") - Para haver escopo, é preciso estar autenticado, então n precisa deixar a verificação de autenticação
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

   @interface Pedidos {

        /*PreAuthorize verifica o comando antes da execução do método
        PostAuthorize deixa o método ser executado, mas ele faz a verificação depois da sua execução
        Usa PostAuthorize somente qnd tem ctz q a execução do método n gera algum efeito colateral (ex: altera estado do pedido no BD

        returnObject é uma variável implícita na expressão para obter a instância do obj retornado no método. Isso funciona no PostAuthorize*/
        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.getUsuarioId() == returnObject.body.cliente.id or "         //O cliente do pedido pode consultar o pedido dele
                + "@algaSecurity.gerenciaRestaurante(returnObject.body.restaurante.id)")     //O usuário autenticado gerencia o restaurante do pedido que está sendo retornado
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeBuscar { }           //Para pedido único

        @PreAuthorize("hasAuthority('SCOPE_READ') and (hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.getUsuarioId() == #filtro.clienteId or"
                + "@algaSecurity.gerenciaRestaurante(#filtro.restauranteId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodePesquisar { }         //Para lista de pedidos

        @PreAuthorize("hasAuthority('SCOPE_WRITE')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeCriar { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('GERENCIAR_PEDIDOS') or "
                + "@algaSecurity.gerenciaRestauranteDoPedido(#codigoPedido))")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarPedidos { }
    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and @algaSecurity.getUsuarioId() == #usuarioId")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarPropriaSenha { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
                + "@algaSecurity.getUsuarioId() == #usuarioId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarUsuario { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('CONSULTAR_USUARIOS_GRUPOS_PERMISSOES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Estatisticas {

        @PreAuthorize("hasAuthority('SCOPE_READ') and hasAuthority('GERAR_RELATORIOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }
}
