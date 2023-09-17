package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.ProdutoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.ProdutoDTO;
import com.algaworks.algafood.domain.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = Constants.SECURITY_SCHEME_NAME)
@Tag(name = Constants.TAG_PRODUTO)
public interface RestauranteProdutoControllerOpenApi {

	@Operation(summary = "Lista os produtos de um restaurante", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<List<ProdutoDTO>> listar(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "Incluir inativos", example = "false") Boolean incluirInativos);

	@Operation(summary = "Busca um produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<ProdutoDTO> buscar(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

	@Operation(summary = "Cadastra um produto de um restaurante", responses = {
			@ApiResponse(responseCode = "201", description = "Produto cadastrado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<ProdutoDTO> salvar(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@RequestBody(description = "Representação de um novo produto", required = true) ProdutoRequestDTO requestDTO);

	@Operation(summary = "Atualiza um produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200", description = "Produto atualizado"),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<ProdutoDTO> atualizar(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "ID do produto", example = "1", required = true) Long produtoId,
			@RequestBody(description = "Representação de um produto com os novos dados", required = true) ProdutoRequestDTO requestDTO);

}