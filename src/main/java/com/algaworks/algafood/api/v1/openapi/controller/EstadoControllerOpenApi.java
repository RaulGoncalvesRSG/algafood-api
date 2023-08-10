package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.EstadoDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Estados")
public interface EstadoControllerOpenApi {

	@ApiOperation("Lista os estados")
	ResponseEntity<CollectionModel<EstadoDTO>> listar();

	@ApiOperation("Busca um estado por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do estado inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)})
	ResponseEntity<EstadoDTO> buscar(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

	@ApiOperation("Cadastra um estado")
	@ApiResponses({@ApiResponse(code = 201, message = "Estado cadastrado")})
	ResponseEntity<EstadoDTO> adicionar(@ApiParam(name = "corpo", value = "Representação de um novo estado", required = true) EstadoRequestDTO requestDTO);

	@ApiOperation("Atualiza um estado por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Estado atualizado"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)})
	ResponseEntity<EstadoDTO> atualizar(
			@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId,
			@ApiParam(name = "corpo", value = "Representação de um estado com os novos dados", required = true) EstadoRequestDTO requestDTO);

	@ApiOperation("Exclui um estado por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Estado excluído"),
			@ApiResponse(code = 404, message = "Estado não encontrado", response = Problem.class)})
	ResponseEntity<Void> remover(@ApiParam(value = "ID de um estado", example = "1", required = true) Long estadoId);

}