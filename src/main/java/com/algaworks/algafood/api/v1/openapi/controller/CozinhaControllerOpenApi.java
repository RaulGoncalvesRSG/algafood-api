package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.CozinhaRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.CozinhaDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Cozinhas")
public interface CozinhaControllerOpenApi {

	@ApiOperation("Lista as cozinhas com paginação")
	ResponseEntity<PagedModel<CozinhaDTO>> listar(Pageable pageable);

	@ApiOperation("Busca uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da cozinha inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)})
	ResponseEntity<CozinhaDTO> buscar(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

	@ApiOperation("Cadastra uma cozinha")
	@ApiResponses({@ApiResponse(code = 201, message = "Cozinha cadastrada")})
	ResponseEntity<CozinhaDTO> adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cozinha", required = true) CozinhaRequestDTO requestDTO);

	@ApiOperation("Atualiza uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Cozinha atualizada"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)})
	ResponseEntity<CozinhaDTO> atualizar(
			@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId,
			@ApiParam(name = "corpo", value = "Representação de uma cozinha com os novos dados", required = true) CozinhaRequestDTO requestDTO);

	@ApiOperation("Exclui uma cozinha por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Cozinha excluída"),
			@ApiResponse(code = 404, message = "Cozinha não encontrada", response = Problem.class)})
	ResponseEntity<Void> remover(@ApiParam(value = "ID de uma cozinha", example = "1", required = true) Long cozinhaId);

}
