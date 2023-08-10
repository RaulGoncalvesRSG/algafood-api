package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.FormaPagamentoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.FormaPagamentoDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Formas de pagamento")
public interface FormaPagamentoControllerOpenApi {

	@ApiOperation("Lista as formas de pagamento")
	@ApiResponses({@ApiResponse(code = 200, message = "OK")})
	//@ApiResponses({@ApiResponse(code = 200, message = "OK", response = FormasPagamentoModelOpenApi.class)})		//Quando usa o ResponseEntity com CollectionModel, o ApiResponse precisa do parâmetro response
	ResponseEntity<List<FormaPagamentoDTO>> listar();

	@ApiOperation("Busca uma forma de pagamento por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID da forma de pagamento inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)})
	ResponseEntity<FormaPagamentoDTO> buscar(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@ApiOperation("Cadastra uma forma de pagamento")
	@ApiResponses({@ApiResponse(code = 201, message = "Forma de pagamento cadastrada")})
	ResponseEntity<FormaPagamentoDTO> adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoRequestDTO requestDTO);

	@ApiOperation("Atualiza uma cidade por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Forma de pagamento atualizada"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)})
	ResponseEntity<FormaPagamentoDTO> atualizar(
			@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId,
			@ApiParam(name = "corpo", value = "Representação de uma forma de pagamento com os novos dados", required = true) FormaPagamentoRequestDTO requestDTO);

	@ApiOperation("Exclui uma forma de pagamento por ID")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Forma de pagamento excluída"),
			@ApiResponse(code = 404, message = "Forma de pagamento não encontrada", response = Problem.class)})
	ResponseEntity<Void> remover(@ApiParam(value = "ID de uma forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}
