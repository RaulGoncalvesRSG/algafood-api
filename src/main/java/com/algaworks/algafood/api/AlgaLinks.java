package com.algaworks.algafood.api;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.controller.EstatisticasController;
import com.algaworks.algafood.api.controller.FluxoPedidoController;
import com.algaworks.algafood.api.controller.FormaPagamentoController;
import com.algaworks.algafood.api.controller.GrupoController;
import com.algaworks.algafood.api.controller.PedidoController;
import com.algaworks.algafood.api.controller.GrupoPermissaoController;
import com.algaworks.algafood.api.controller.RestauranteController;
import com.algaworks.algafood.api.controller.RestauranteFormaPagamentoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoController;
import com.algaworks.algafood.api.controller.RestauranteProdutoFotoController;
import com.algaworks.algafood.api.controller.RestauranteUsuarioResponsavelController;
import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
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

	private final Class<UsuarioController> usuarioControllerClass = UsuarioController.class;
	private final Class<UsuarioGrupoController> usuarioGrupoControllerClass = UsuarioGrupoController.class;
	private final Class<GrupoPermissaoController> grupoPermissaoControllerClass = GrupoPermissaoController.class;
	private final Class<PedidoController> pedidoControllerClass = PedidoController.class;
	private final Class<CozinhaController> cozinhaControllerClass = CozinhaController.class;
	private final Class<RestauranteController> restauranteControllerClass = RestauranteController.class;
	private final Class<RestauranteProdutoFotoController> restauranteProdutoFotoControllerClass = RestauranteProdutoFotoController.class;
	private final Class<FluxoPedidoController> fluxoPedidoControllerClass = FluxoPedidoController.class;
	private final Class<RestauranteFormaPagamentoController> restauranteFormaPagamentoControllerClass  = RestauranteFormaPagamentoController.class;
	private final Class<EstatisticasController> estatisticasControllerClass  = EstatisticasController.class;

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
		String pedidosUrl = linkTo(pedidoControllerClass).toUri().toString();
		return Link.of(UriTemplate.of(pedidosUrl,PAGINACAO_VARIABLES.concat(FILTRO_VARIABLES)), linkRelations);
	}

	public Link linkToConfirmacaoPedido(String codigoPedido, String linkRelations) {
		return linkTo(methodOn(fluxoPedidoControllerClass)
				.confirmar(codigoPedido)).withRel(linkRelations);
	}

	public Link linkToEntregaPedido(String codigoPedido, String linkRelations) {
		return linkTo(methodOn(fluxoPedidoControllerClass)
				.entregar(codigoPedido)).withRel(linkRelations);
	}

	public Link linkToCancelamentoPedido(String codigoPedido, String linkRelations) {
		return linkTo(methodOn(fluxoPedidoControllerClass)
				.cancelar(codigoPedido)).withRel(linkRelations);
	}

	public Link linkToRestaurante(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteControllerClass)
				.buscar(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestaurante(Long restauranteId) {
		return linkToRestaurante(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestaurantes(String linkRelations) {
		String restaurantesUrl = linkTo(restauranteControllerClass).toUri().toString();
		return Link.of(UriTemplate.of(restaurantesUrl, PROJECAO_VARIABLES), linkRelations);
	}

	public Link linkToRestaurantes() {
		return linkToRestaurantes(IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteFormaPagamentoControllerClass)
				.listarFormasPagamento(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestauranteFormasPagamento(Long restauranteId) {
		return linkToRestauranteFormasPagamento(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteFormaPagamentoDesassociacao(Long restauranteId, Long formaPagamentoId, String linkRelations) {
		return linkTo(methodOn(restauranteFormaPagamentoControllerClass)
				.desassociar(restauranteId, formaPagamentoId)).withRel(linkRelations);
	}

	public Link linkToRestauranteFormaPagamentoAssociacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteFormaPagamentoControllerClass)
				.associar(restauranteId, null)).withRel(linkRelations);
	}

	public Link linkToRestauranteAbertura(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteControllerClass)
				.abrir(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestauranteFechamento(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteControllerClass)
				.fechar(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestauranteInativacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteControllerClass)
				.inativar(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestauranteAtivacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(restauranteControllerClass)
				.ativar(restauranteId)).withRel(linkRelations);
	}

	public Link linkToUsuario(Long usuarioId, String linkRelations) {
		return linkTo(methodOn(usuarioControllerClass)
				.buscar(usuarioId)).withRel(linkRelations);
	}

	public Link linkToUsuario(Long usuarioId) {
		return linkToUsuario(usuarioId, IanaLinkRelations.SELF.value());
	}

	/*Em métodos que tem parâmetros, é preciso usar o methodOn para que o HATEOAS descubra quem são eles e possa utilizá-los corretamente
    Sem usar o methodOn, estará fazendo referência ao @RequestMapping que está anotado dentro do Controller,
    que dá na mesma do método listar(), já que o listar() não tem nenhum path diferente do @RequestMapping*/
	public Link linkToUsuarios(String linkRelations) {
		return linkTo(usuarioControllerClass).withRel(linkRelations);
	}

	public Link linkToUsuarios() {
		return linkToUsuarios(IanaLinkRelations.SELF.value());
	}

	public Link linkToUsuarioGrupoAssociacao(Long usuarioId, String linkRelations) {
		return linkTo(methodOn(usuarioGrupoControllerClass)
				.associar(usuarioId, null)).withRel(linkRelations);
	}

	public Link linkToUsuarioGrupoDesassociacao(Long usuarioId, Long grupoId, String linkRelations) {
		return linkTo(methodOn(usuarioGrupoControllerClass)
				.desassociar(usuarioId, grupoId)).withRel(linkRelations);
	}

	public Link linkToGruposUsuario(Long usuarioId, String linkRelations) {
		return linkTo(methodOn(usuarioGrupoControllerClass)
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
		return linkTo(grupoPermissaoControllerClass).withRel(linkRelations);
	}

	public Link linkToPermissoes() {
		return linkToPermissoes(IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissoes(Long grupoId, String linkRelations) {
		return linkTo(methodOn(grupoPermissaoControllerClass)
				.listar(grupoId)).withRel(linkRelations);
	}

	public Link linkToGrupoPermissoes(Long grupoId) {
		return linkToGrupoPermissoes(grupoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToGrupoPermissaoAssociacao(Long grupoId, String linkRelations) {
		return linkTo(methodOn(grupoPermissaoControllerClass)
				.associar(grupoId, null)).withRel(linkRelations);
	}

	public Link linkToGrupoPermissaoDesassociacao(Long grupoId, Long permissaoId, String linkRelations) {
		return linkTo(methodOn(grupoPermissaoControllerClass)
				.desassociar(grupoId, permissaoId)).withRel(linkRelations);
	}

	public Link linkToRestauranteResponsaveis(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.listar(restauranteId)).withRel(linkRelations);
	}

	public Link linkToRestauranteResponsaveis(Long restauranteId) {
		return linkToRestauranteResponsaveis(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToRestauranteResponsavelDesassociacao(Long restauranteId, Long usuarioId, String linkRelations) {

		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.desassociar(restauranteId, usuarioId)).withRel(linkRelations);
	}

	public Link linkToRestauranteResponsavelAssociacao(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
				.associar(restauranteId, null)).withRel(linkRelations);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId, String linkRelations) {
		return linkTo(methodOn(FormaPagamentoController.class)
				.buscar(formaPagamentoId)).withRel(linkRelations);
	}

	public Link linkToFormaPagamento(Long formaPagamentoId) {
		return linkToFormaPagamento(formaPagamentoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFormasPagamento(String linkRelations) {
		return linkTo(FormaPagamentoController.class).withRel(linkRelations);
	}

	public Link linkToFormasPagamento() {
		return linkToFormasPagamento(IanaLinkRelations.SELF.value());
	}

	public Link linkToCidade(Long cidadeId, String linkRelations) {
		return linkTo(methodOn(CidadeController.class)
				.buscar(cidadeId)).withRel(linkRelations);
	}

	public Link linkToCidade(Long cidadeId) {
		return linkToCidade(cidadeId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCidades(String linkRelations) {
		return linkTo(CidadeController.class).withRel(linkRelations);
	}

	public Link linkToCidades() {
		return linkToCidades(IanaLinkRelations.SELF.value());
	}

	public Link linkToEstado(Long estadoId, String linkRelations) {
		return linkTo(methodOn(EstadoController.class)
				.buscar(estadoId)).withRel(linkRelations);
	}

	public Link linkToEstado(Long estadoId) {
		return linkToEstado(estadoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstados(String linkRelations) {
		return linkTo(EstadoController.class).withRel(linkRelations);
	}

	public Link linkToEstados() {
		return linkToEstados(IanaLinkRelations.SELF.value());
	}

	public Link linkToProduto(Long restauranteId, Long produtoId, String linkRelations) {
		return linkTo(methodOn(RestauranteProdutoController.class)
				.buscar(restauranteId, produtoId))
				.withRel(linkRelations);
	}

	public Link linkToProduto(Long restauranteId, Long produtoId) {
		return linkToProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToProdutos(Long restauranteId, String linkRelations) {
		return linkTo(methodOn(RestauranteProdutoController.class)
				.listar(restauranteId, null)).withRel(linkRelations);
	}

	public Link linkToProdutos(Long restauranteId) {
		return linkToProdutos(restauranteId, IanaLinkRelations.SELF.value());
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId, String linkRelations) {
		return linkTo(methodOn(restauranteProdutoFotoControllerClass)
				.buscar(restauranteId, produtoId)).withRel(linkRelations);
	}

	public Link linkToFotoProduto(Long restauranteId, Long produtoId) {
		return linkToFotoProduto(restauranteId, produtoId, IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinhas(String linkRelations) {
		return linkTo(cozinhaControllerClass).withRel(linkRelations);
	}

	public Link linkToCozinhas() {
		return linkToCozinhas(IanaLinkRelations.SELF.value());
	}

	public Link linkToCozinha(Long cozinhaId, String linkRelations) {
		return linkTo(methodOn(cozinhaControllerClass)
				.buscar(cozinhaId)).withRel(linkRelations);
	}

	public Link linkToCozinha(Long cozinhaId) {
		return linkToCozinha(cozinhaId, IanaLinkRelations.SELF.value());
	}

	public Link linkToEstatisticas(String linkRelations) {
		return linkTo(estatisticasControllerClass).withRel(linkRelations);
	}

	public Link linkToEstatisticasVendasDiarias(String linkRelations) {
		TemplateVariables filtroVariables = new TemplateVariables(
				new TemplateVariable("restauranteId", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoInicio", VariableType.REQUEST_PARAM),
				new TemplateVariable("dataCriacaoFim", VariableType.REQUEST_PARAM),
				new TemplateVariable("timeOffset", VariableType.REQUEST_PARAM));

		String pedidosUrl = linkTo(methodOn(estatisticasControllerClass)
				.consultarVendasDiarias(null, null)).toUri().toString();

		return Link.of(UriTemplate.of(pedidosUrl, filtroVariables), linkRelations);
	}

}
