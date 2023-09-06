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

        @PreAuthorize("@algaSecurity.podeConsultarCozinhas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

     @interface Restaurantes {

        @PreAuthorize("@algaSecurity.podeGerenciarCadastroRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeGerenciarCadastro { }

         //O responsável pelo seu restaurante n pode editar os dados do seu próprio restaurante
         //O @ no @algaSecurity dá acesso a instância de um Bean, então consegue fazer a chamada de algum método. O padrão do bean é inicial minúsculo
         /*# - Esse valor vem de uma variável que está disponível em tempo de requisição, vindo do path variable do endpoint, para ler tal variável é usado o #
         na expression language do Spring. Assim como o @ para acessar os beans*/
         @PreAuthorize("@algaSecurity.podeGerenciarFuncionamentoRestaurantes(#id)")
         @Retention(RUNTIME)
         @Target(METHOD)
         @interface PodeGerenciarFuncionamento { }

         //@PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()") - Para haver escopo, é preciso estar autenticado, então n precisa deixar a verificação de autenticação
        // @PreAuthorize("hasAuthority('SCOPE_READ')")
         @PreAuthorize("@algaSecurity.podeConsultarRestaurantes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

   @interface Pedidos {

        /*PreAuthorize verifica o comando antes da execução do método
        PostAuthorize deixa o método ser executado, mas ele faz a verificação depois da sua execução
        Usa PostAuthorize somente qnd tem ctz q a execução do método n gera algum efeito colateral (ex: altera estado do pedido no BD

        returnObject é uma variável implícita na expressão para obter a instância do obj retornado no método. Isso funciona no PostAuthorize*/
        @PreAuthorize("hasAuthority('SCOPE_READ') and isAuthenticated()")
        @PostAuthorize("hasAuthority('CONSULTAR_PEDIDOS') or "
                + "@algaSecurity.usuarioAutenticadoIgual(returnObject.cliente.id) or "	//O cliente do pedido pode consultar o pedido dele
                + "@algaSecurity.gerenciaRestaurante(returnObject.restaurante.id)")		//O usuário autenticado gerencia o restaurante do pedido que está sendo retornado
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeBuscar { }		//Para pedido único

//       @PreAuthorize("@algaSecurity.podePesquisarPedidos()")
//       @PostAuthorize("@algaSecurity.podePesquisarPedidos(returnObject.cliente.id, returnObject.restaurante.id)")
//       @Retention(RUNTIME)
//       @Target(METHOD)
//       @interface PodeBuscar { }

       @PreAuthorize("@algaSecurity.podePesquisarPedidos(#filtro.clienteId, #filtro.restauranteId)")
       @Retention(RUNTIME)
       @Target(METHOD)
       @interface PodePesquisar { }		//Para lista de pedidos

       @PreAuthorize("hasAuthority('SCOPE_WRITE') and isAuthenticated()")
       @Retention(RUNTIME)
       @Target(METHOD)
       @interface PodeCriar { }

       @PreAuthorize("@algaSecurity.podeGerenciarPedidos(#codigoPedido)")
       @Retention(RUNTIME)
       @Target(METHOD)
       @interface PodeGerenciarPedidos { }
    }

    @interface FormasPagamento {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_FORMAS_PAGAMENTO')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarFormasPagamento()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Cidades {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_CIDADES')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarCidades()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Estados {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and hasAuthority('EDITAR_ESTADOS')")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarEstados()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface UsuariosGruposPermissoes {

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId)")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarPropriaSenha { }

        @PreAuthorize("hasAuthority('SCOPE_WRITE') and (hasAuthority('EDITAR_USUARIOS_GRUPOS_PERMISSOES') or "
                + "@algaSecurity.usuarioAutenticadoIgual(#usuarioId))")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeAlterarUsuario { }

        @PreAuthorize("@algaSecurity.podeEditarUsuariosGruposPermissoes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeEditar { }

        @PreAuthorize("@algaSecurity.podeConsultarUsuariosGruposPermissoes()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }

    @interface Estatisticas {

        @PreAuthorize("@algaSecurity.podeConsultarEstatisticas()")
        @Retention(RUNTIME)
        @Target(METHOD)
        @interface PodeConsultar { }
    }
}
