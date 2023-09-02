package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.ProdutoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.ProdutoDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Produtos")
public interface RestauranteProdutoControllerOpenApi {

	@ApiOperation("Lista os produtos de um restaurante")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do restaurante inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<List<ProdutoDTO>> listar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "Indica se deve ou não incluir produtos inativos no resultado da listagem", example = "false", defaultValue = "false") Boolean incluirInativos);

	@ApiOperation("Busca um produto de um restaurante")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do restaurante ou produto inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)})
	ResponseEntity<ProdutoDTO> buscar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId);

	@ApiOperation("Cadastra um produto de um restaurante")
	@ApiResponses({
			@ApiResponse(code = 201, message = "Produto cadastrado"),
			@ApiResponse(code = 404, message = "Restaurante não encontrado", response = Problem.class)})
	ResponseEntity<ProdutoDTO> salvar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(name = "corpo", value = "Representação de um novo produto", required = true) ProdutoRequestDTO requestDTO);

	@ApiOperation("Atualiza um produto de um restaurante")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Produto atualizado"),
			@ApiResponse(code = 404, message = "Produto de restaurante não encontrado", response = Problem.class)})
	ResponseEntity<ProdutoDTO> atualizar(
			@ApiParam(value = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@ApiParam(value = "ID do produto", example = "1", required = true) Long produtoId,
			@ApiParam(name = "corpo", value = "Representação de um produto com os novos dados", required = true) ProdutoRequestDTO requestDTO);

}