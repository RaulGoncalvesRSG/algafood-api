package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.RestauranteRequestDTO;
import com.algaworks.algafood.api.dto.response.RestauranteDTO;
import com.algaworks.algafood.api.dto.view.RestaruanteView;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.openapi.model.RestauranteBasicoModelOpenApi;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Restaurantes")
public interface RestauranteControllerOpenApi {

	/*A documentação do swagger para a operação Lista restaurantes vai ser apenas uma
	allowableValues informa o parâmetro permitido, pode passar "apenas-nome" ou nada
	response = RestauranteBasicoModelOpenApi: informa para o swagger substituir o objeto de retorno do endpoint pelo
	que foi especificado no repsonse. OBS: Isso é feito apenas quando está usando @JsonView*/
	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
	@ApiImplicitParams({
			@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", name = "projecao", paramType = "query", type = "string")})
	@JsonView(RestaruanteView.Resumo.class)
	ResponseEntity<List<RestauranteDTO>> listar();

	@ApiOperation(value = "Lista restaurantes", hidden = true) //hidden = true oculta a ApiOperation
	ResponseEntity<List<RestauranteDTO>> listarApenasNomes();

	@ApiOperation("Busca um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<RestauranteDTO> buscar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Cadastra um restaurante")
	@ApiResponses({@ApiResponse(code = 201, message = "Restaurante cadastrado")})
	ResponseEntity<RestauranteDTO> adicionar(@ApiParam(name = "corpo", value = "Representação de um novo restaurante", required = true) RestauranteRequestDTO requestDTO);

	@ApiOperation("Atualiza um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Restaurante atualizado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<RestauranteDTO> atualizar(
			@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(name = "corpo", value = "Representação de um restaurante com os novos dados", required = true) RestauranteRequestDTO requestDTO);

	@ApiOperation("Ativa um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante ativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<Void> ativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Inativa um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante inativado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<Void> inativar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Ativa múltiplos restaurantes")
	@ApiResponses({@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")})
	ResponseEntity<Void> ativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Inativa múltiplos restaurantes")
	@ApiResponses({@ApiResponse(code = 204, message = "Restaurantes ativados com sucesso")})
	ResponseEntity<Void> inativarMultiplos(@ApiParam(name = "corpo", value = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@ApiOperation("Abre um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante aberto com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<Void> abrir(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

	@ApiOperation("Fecha um restaurante por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Restaurante fechado com sucesso"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<Void> fechar(@ApiParam(value = "ID de um restaurante", example = "1", required = true) Long restauranteId);

}
