package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.FotoProdutoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.FotoProdutoDTO;
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
import org.springframework.web.HttpMediaTypeNotAcceptableException;

import java.io.IOException;

@SecurityRequirement(name = Constants.SECURITY_SCHEME_NAME)
@Tag(name = Constants.TAG_PRODUTO)
public interface RestauranteProdutoFotoControllerOpenApi {

	@Operation(description = "Atualiza a foto do produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200", description = "Foto do produto atualizada"),
			@ApiResponse(responseCode = "404", description = "Produto de restaurante não encontrado"),
	})
	ResponseEntity<FotoProdutoDTO> atualizarFoto(
			@Parameter(description = "Id do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "Id do produto", example = "2", required = true) Long produtoId,
			@RequestBody(required = true) FotoProdutoRequestDTO requestDTO) throws IOException;

	@Operation(summary = "Exclui a foto do produto de um restaurante", responses = {
			@ApiResponse(responseCode = "204", description = "Foto do produto excluída"),
			@ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> excluir(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

	@Operation(summary = "Busca a foto do produto de um restaurante", responses = {
			@ApiResponse(responseCode = "200", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = FotoProdutoDTO.class)),
					@Content(mediaType = "image/jpeg", schema = @Schema(type = "string", format = "binary")),
					@Content(mediaType = "image/png", schema = @Schema(type = "string", format = "binary"))
			}),
			@ApiResponse(responseCode = "400", description = "ID do restaurante ou produto inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Foto de produto não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) }),

	})
	ResponseEntity<FotoProdutoDTO> buscar(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "ID do produto", example = "1", required = true) Long produtoId);

	//hidden = true pq este método e o método de buscar respondem na mesma URl utilizando o verbo GET. hidden = true deixa o endpoint oculto no swagger, mas o endpoint continua funcionando normalmente
	@Operation(description = "Busca a foto do produto de um restaurante", hidden = true)
	ResponseEntity<?> exibirFoto(Long restauranteId, Long produtoId, String acceptHeader) throws HttpMediaTypeNotAcceptableException;

}