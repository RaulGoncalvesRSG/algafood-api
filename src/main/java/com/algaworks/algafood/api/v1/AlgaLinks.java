package com.algaworks.algafood.api.v1;

import com.algaworks.algafood.api.v1.controller.CidadeController;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.controller.EstatisticasController;
import com.algaworks.algafood.api.v1.controller.FluxoPedidoController;
import com.algaworks.algafood.api.v1.controller.FormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.GrupoController;
import com.algaworks.algafood.api.v1.controller.PedidoController;
import com.algaworks.algafood.api.v1.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.v1.controller.RestauranteController;
import com.algaworks.algafood.api.v1.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.v1.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.v1.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.controller.UsuarioGrupoController;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.TemplateVariable;
import org.springframework.hateoas.TemplateVariable.VariableType;
import org.springframework.hateoas.TemplateVariables;
import org.springframework.hateoas.UriTemplate;
import org.springframework.stereotype.Component;

//WebMvcLinkBuilder - construtor de link dinâmico. Ele adiciona protocolo, domínio e porta da aplicação para conseguir formar a URL completa do endpoint
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class AlgaLinks {

	private final Class<UsuarioController> usuarioController = UsuarioController.class;
	private final Class<UsuarioGrupoController> usuarioGrupoController = UsuarioGrupoController.class;
	private final Class<GrupoPermissaoController> grupoPermissaoController = GrupoPermissaoController.class;
	private final Class<PedidoController> pedidoController = PedidoController.class;
	private final Class<CozinhaController> cozinhaController = CozinhaController.class;
	private final Class<RestauranteController> restauranteController = RestauranteController.class;
	private final Class<RestauranteProdutoFotoController> restauranteProdutoFotoController = RestauranteProdutoFotoController.class;
	private final Class<FluxoPedidoController> fluxoPedidoController = FluxoPedidoController.class;
	private final Class<RestauranteFormaPagamentoController> restauranteFormaPagamentoController = RestauranteFormaPagamentoController.class;
	private final Class<EstatisticasController> estatisticasController = EstatisticasController.class;
	private final Class<RestauranteProdutoController> restauranteProdutoController = RestauranteProdutoController.class;
	private final Class<CidadeController> cidadeController = CidadeController.class;
	private final Class<EstadoController> estadoController = EstadoController.class;
	private final Class<FormaPagamentoController> formaPagamentoController = FormaPagamentoController.class;
	private final Class<RestauranteUsuarioResponsavelController> restauranteUsuarioResponsavelController = RestauranteUsuarioResponsavelController.class;

    private static final String VERBO_GET = "GET";
    private static final String VERBO_PUT = "PUT";

	//Parâmetros da paginação
	public static final TemplateVariables PAGINACAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("page", VariableType.REQUEST_PARAM),
			new TemplateVariable("size", VariableType.REQUEST_PARAM),
			new TemplateVariable("sort", VariableType.REQUEST_PARAM));

	//Parâmetros de filtro
	public static final TemplateVariables FILTRO_VARIABLES = new TemplateVariables(
			new TemplateVariable("clienteId", TemplateVariable.VariableType.REQUEST_PARAM),
			new TemplateVariable("restauranteId", TemplateVariable.VariableType.REQUEST_PARAM),
			new TemplateVariable("dataCriacaoInicio", TemplateVariable.VariableType.REQUEST_PARAM),
			new TemplateVariable("dataCriacaoFim", TemplateVariable.VariableType.REQUEST_PARAM));

	public static final TemplateVariables PROJECAO_VARIABLES = new TemplateVariables(
			new TemplateVariable("projecao", VariableType.REQUEST_PARAM));

	public Link linkToPedidos(String linkRelations) {
		//Resultado de variáveis de template: "URL/pedidos{?page,size,sort,clienteId,restauranteId...}"
		String pedidosUrl = linkTo(pedidoController).toUri().toString();
		return Link.of(UriTemplate.of(pedidosUrl,PAGINACAO_VARIABLES.concat(FILTRO_VARIABLES)), linkRelations);
	}

	public Link linkToConfirmacaoPedido(String codigoPedido, String linkRelations) {
		return linkTo(methodOn(fluxoPedidoController)
				.confirmar(codigoPedido))
                .withRel(linkRelations)
                .withType(VERBO_PUT);
	}

	public Link linkToEntregaPedido(String codigoPedido, String linkRelations) {
		return linkTo(methodOn(fluxoPedidoController)
				.entregar(codigoPedido))
                .withRel(linkRelations)
                .withType(VERBO_PUT);
	}

	public Link linkToCancelamentoPedido(String codigoPedido, String linkRelations) {
		return linkTo(methodOn(fluxoPedidoController)
				.cancelar(codigoPedido))
                .withRel(linkRelations)
                .withType(VERBO_PUT);
	}

	public Link linkToRestaurante(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteController)
				.buscar(restauranteId))
                .withRel(linkRelations)
                .withType(VERBO_GET);
	}

	public Link linkToRestaurante(Long restauranteId) {
		return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestaurantes(String linkRelations) {
		String restaurantesUrl = linkTo(restauranteController).toUri().toString();
		return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), linkRelations);
	}

	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteFormaPagamentoController)
				.listarFormasPagamento(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
		return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String linkRelations) {
		return linkTo(methodOn(restauranteFormaPagamentoController)
				.desassociar(restauranteId, formaPagamentoId)).withRel(linkRelations);
	}

	public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteFormaPagamentoController)
				.associar(restauranteId, null)).withRel(linkRelations);
	}

	public Link linkToRestauranteAbertura(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteController)
				.abrir(restauranteId))
				.withRel(linkRelations)
				.withType(VERBO_PUT);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteController)
				.fechar(restauranteId)).withRel(linkRelations)
				.withType(VERBO_PUT);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteController)
				.inativar(restauranteId)).withRel(linkRelations)
				.withType(VERBO_PUT);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteController)
				.ativar(restauranteId)).withRel(linkRelations)
				.withType(VERBO_PUT);
	}

	public Link linkToUsuario(Long usuarioId, String linkRelations) {
		return linkTo(methodOn(usuarioController)
				.buscar(usuarioId)).withRel(linkRelations);
	}

	public Link linkToUsuario(Long usuarioId) {
		return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	/*Em métodos que tem parâmetros, é preciso usar o methodOn para que o HATEOAS descubra quem são eles e possa utilizá-los corretamente
    Sem usar o methodOn, estará fazendo referência ao @RequestMapping que está anotado dentro do Controller,
    que dá na mesma do método listar(), já que o listar() não tem nenhum path diferente do @RequestMapping*/
	public Link linkToUsuarios(String linkRelations) {
		return linkTo(usuarioController).withRel(linkRelations);
	}

	public Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String linkRelations) {
		return linkTo(methodOn(usuarioGrupoController)
				.associar(usuarioId, null)).withRel(linkRelations);
	}

	public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String linkRelations) {
		return linkTo(methodOn(usuarioGrupoController)
				.desassociar(usuarioId, grupoId)).withRel(linkRelations);
	}

	public Link linkToGruposUsuario(Long usuarioId, String linkRelations) {
		return linkTo(methodOn(usuarioGrupoController)
				.listar(usuarioId)).withRel(linkRelations);
	}

	public Link linkToGruposUsuario(Long usuarioId) {
		return linkToGruposUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupos(String linkRelations) {
		return linkTo(GrupoController.class).withRel(linkRelations);
	}

	public Link linkToGrupos() {
		return linkToGrupos(IanaLinkRelations.SELF.value());
	}

	public Link linkToPermissoes(String linkRelations) {
		return linkTo(grupoPermissaoController).withRel(linkRelations);
	}

	public Link linkToPermissoes() {
		return linkToPermissoes(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId, String linkRelations) {
		return linkTo(methodOn(grupoPermissaoController)
				.listar(grupoId)).withRel(linkRelations);
	}

	public Link linkToGrupoPermissoes(Long grupoId) {
		return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String linkRelations) {
		return linkTo(methodOn(grupoPermissaoController)
				.associar(grupoId, null)).withRel(linkRelations);
	}

	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String linkRelations) {
		return linkTo(methodOn(grupoPermissaoController)
				.desassociar(grupoId, permissaoId)).withRel(linkRelations);
	}

	public Link linkToRestauranteResponsaveis(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteUsuarioResponsavelController)
				.listar(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestauranteResponsaveis(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String linkRelations) {

		return linkTo(methodOn(restauranteUsuarioResponsavelController)
				.desassociar(restauranteId, usuarioId)).withRel(linkRelations);
	}

	public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteUsuarioResponsavelController)
				.associar(restauranteId, null)).withRel(linkRelations);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String linkRelations) {
		return linkTo(methodOn(formaPagamentoController)
				.buscar(formaPagamentoId)).withRel(linkRelations);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFormasPagamento(String linkRelations) {
		return linkTo(formaPagamentoController).withRel(linkRelations);
	}

	public Link linkToFormasPagamento() {
		return linkToFormasPagamento(IanaLinkRelations.SELF.value());
	}

	public Link linkToCidade(Long cidadeId, String linkRelations) {
		return linkTo(methodOn(cidadeController)
				.buscar(cidadeId)).withRel(linkRelations);
	}

	public Link linkToCidade(Long cidadeId) {
		return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String linkRelations) {
		return linkTo(cidadeController).withRel(linkRelations);
	}

	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToEstado(Long estadoId, String linkRelations) {
		return linkTo(methodOn(estadoController)
				.buscar(estadoId)).withRel(linkRelations);
	}

	public Link linkToEstado(Long estadoId) {
		return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstados(String linkRelations) {
		return linkTo(estadoController).withRel(linkRelations);
	}

	public Link linkToEstados() {
		return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String linkRelations) {
		return linkTo(methodOn(restauranteProdutoController)
				.buscar(restauranteId, produtoId))
				.withRel(linkRelations);
	}

	public Link linkToProduto(Long restauranteId, Long produtoId) {
		return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToProdutos(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteProdutoController)
				.listar(restauranteId, null)).withRel(linkRelations);
	}

	public Link linkToProdutos(Long restauranteId) {
		return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId, String linkRelations) {
		return linkTo(methodOn(restauranteProdutoFotoController)
				.buscar(restauranteId, produtoId)).withRel(linkRelations);
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
		return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinhas(String linkRelations) {
		return linkTo(cozinhaController).withRel(linkRelations);
	}

	public Link linkToCozinhas() {
		return linkToCozinhas(IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinha(Long cozinhaId, String linkRelations) {
		return linkTo(methodOn(cozinhaController)
				.buscar(cozinhaId)).withRel(linkRelations);
	}

	public Link linkToCozinha(Long cozinhaId) {
		return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstatisticas(String linkRelations) {
		return linkTo(estatisticasController).withRel(linkRelations);
	}

	public Link linkToEstatisticasVendasDiarias(String linkRelations) {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
				new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));

		String pedidosUrl = linkTo(methodOn(estatisticasController)
				.consultarVendasDiarias(null, null)).toUri().toString();

		return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), linkRelations);
	}
}
