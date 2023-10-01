package com.algaworks.algafood.core.security;

import com.algaworks.algafood.domain.repository.PedidoRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class AlgaSecurity {

    private final RestauranteRepository restauranteRepository;
    private final PedidoRepository pedidoRepository;

    private static final String SCOPE_WRITE = "SCOPE_WRITE";
    private static final String SCOPE_READ = "SCOPE_READ";
    private static final String GERENCIA_PEDIDOS = "GERENCIAR_PEDIDOS";
    private static final String EDITAR_RESTAURANTES = "EDITAR_RESTAURANTES";
    private static final String CONSULTAR_USUARIOS_GRUPOS_PERMISSOES = "CONSULTAR_USUARIOS_GRUPOS_PERMISSOES";
    private static final String EDITAR_USUARIOS_GRUPOS_PERMISSOES = "EDITAR_USUARIOS_GRUPOS_PERMISSOES";
    private static final String CONSULTAR_PEDIDOS = "CONSULTAR_PEDIDOS";
    private static final String GERAR_RELATORIOS = "GERAR_RELATORIOS";

    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();  //Representa o obj Token da requição atual
    }

    public boolean isAutenticado() {
        return getAuthentication().isAuthenticated();
    }

    public Long getUsuarioId() {
        Jwt jwt = (Jwt) getAuthentication().getPrincipal();     //Token JWT com todas informações autocontidas

        Object usuarioId = jwt.getClaim("usuario_id");

        //Na versão mais recente do JWT, o obj está sendo serializado como String
        return Objects.isNull(usuarioId)? null : Long.valueOf(usuarioId.toString());
    }

    public boolean gerenciaRestaurante(Long restauranteId) {
        if (Objects.isNull(restauranteId)) {
            return false;
        }
        return restauranteRepository.existsResponsavel(restauranteId, getUsuarioId());
    }

    //Verifica se o usuário logado é responsácvel pelo restaurante do pedido
    public boolean gerenciaRestauranteDoPedido(String codigoPedido) {
        return pedidoRepository.isPedidoGerenciadoPor(codigoPedido, getUsuarioId());
    }

    public boolean usuarioAutenticadoIgual(Long usuarioId) {
        return Objects.nonNull(getUsuarioId()) && Objects.nonNull(usuarioId) && getUsuarioId().equals(usuarioId);
    }

    public boolean hasAuthority(String authorityName) {
        return getAuthentication().getAuthorities().stream()
                .anyMatch(authority -> authority.getAuthority().equals(authorityName));
    }

    public boolean temEscopoEscrita() {
        return hasAuthority(SCOPE_WRITE);
    }

    public boolean temEscopoLeitura() {
        return hasAuthority(SCOPE_READ);
    }

    public boolean podeGerenciarPedidos(String codigoPedido) {
        return temEscopoEscrita() && (hasAuthority(GERENCIA_PEDIDOS) || gerenciaRestauranteDoPedido(codigoPedido));
    }

    public boolean podeConsultarRestaurantes() {
        return temEscopoLeitura() && isAutenticado();
    }

    public boolean podeGerenciarCadastroRestaurantes() {
        return temEscopoEscrita() && hasAuthority(EDITAR_RESTAURANTES);
    }

    public boolean podeGerenciarFuncionamentoRestaurantes(Long restauranteId) {
        return temEscopoEscrita() && (hasAuthority(EDITAR_RESTAURANTES) || gerenciaRestaurante(restauranteId));
    }

    public boolean podeConsultarUsuariosGruposPermissoes() {
        return temEscopoLeitura() && hasAuthority(CONSULTAR_USUARIOS_GRUPOS_PERMISSOES);
    }

    public boolean podeEditarUsuariosGruposPermissoes() {
        return temEscopoEscrita() && hasAuthority(EDITAR_USUARIOS_GRUPOS_PERMISSOES);
    }

    public boolean podePesquisarPedidos(Long clienteId, Long restauranteId) {
        return temEscopoLeitura() && (hasAuthority(CONSULTAR_PEDIDOS) || usuarioAutenticadoIgual(clienteId) || gerenciaRestaurante(restauranteId));
    }

    public boolean podePesquisarPedidos() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarFormasPagamento() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarCidades() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarEstados() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarCozinhas() {
        return isAutenticado() && temEscopoLeitura();
    }

    public boolean podeConsultarEstatisticas() {
        return temEscopoLeitura() && hasAuthority(GERAR_RELATORIOS);
    }
}
