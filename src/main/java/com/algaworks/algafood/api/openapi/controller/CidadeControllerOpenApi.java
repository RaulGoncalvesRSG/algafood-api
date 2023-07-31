package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.request.CidadeRequestDTO;
import com.algaworks.algafood.api.dto.response.CidadeDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Cidades")
public interface CidadeControllerOpenApi {

	@ApiOperation("Lista as cidades")
	ResponseEntity<List<CidadeDTO>> listar();

	@ApiOperation("Busca uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
	ResponseEntity<CidadeDTO> buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

	@ApiOperation("Cadastra uma cidade")
	@ApiResponses({@ApiResponse(code = 201, message = "Cidade cadastrada")})
	ResponseEntity<CidadeDTO> adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeRequestDTO requestDTO);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Cidade atualizada"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
	ResponseEntity<CidadeDTO> atualizar(
			@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
			@ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true) CidadeRequestDTO requestDTO);

	@ApiOperation("Exclui uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cidade excluída"),
			@ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
	ResponseEntity<Void> remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

}
