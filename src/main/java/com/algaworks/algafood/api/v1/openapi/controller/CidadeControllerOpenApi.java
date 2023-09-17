package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.CidadeRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.CidadeDTO;
import com.algaworks.algafood.domain.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = Constants.SECURITY_SCHEME_NAME)		//Coloca segurança no endpoint
@Tag(name = Constants.TAG_CIDADE)
public interface CidadeControllerOpenApi {

	@Operation(summary = "Lista as cidades")
	ResponseEntity<CollectionModel<CidadeDTO>> listar();

	@Operation(summary = "Busca uma cidade por Id",
			responses = {
					@ApiResponse(responseCode = "200"),
					@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
							content = @Content(schema = @Schema(ref = "Problema"))
					),
					@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
							content = @Content(schema = @Schema(ref = Constants.SCHEMA_PROBLEMA))
					)
			})
	ResponseEntity<CidadeDTO> buscar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long id);

	@Operation(summary = "Cadastra uma cidade", description = "Cadastro de uma cidade, " +
			"necessita de um estado e um nome válido")
	ResponseEntity<CidadeDTO> adicionar(@RequestBody(description = "Representação de uma nova cidade", required = true) CidadeRequestDTO requestDTO);

	@Operation(summary = "Atualizado uma cidade por ID",
			responses = {
					@ApiResponse(responseCode = "200"),
					@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
							content = @Content(schema = @Schema(ref = "Problema"))
					),
					@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
							content = @Content(schema = @Schema(ref = "Problema"))
					)
			})
	ResponseEntity<CidadeDTO> atualizar(
			@Parameter(description = "ID de uma cidade", example = "1", required = true)  Long id,
			@RequestBody(description = "Representação de uma cidade com dados atualizados", required = true) CidadeRequestDTO requestDTO);

	@Operation(summary = "Excluir uma cidade por ID",responses = {
			@ApiResponse(responseCode = "204"),
			@ApiResponse(responseCode = "400", description = "ID da cidade inválido",
					content = @Content(schema = @Schema(ref = "Problema"))
			),
			@ApiResponse(responseCode = "404", description = "Cidade não encontrada",
					content = @Content(schema = @Schema(ref = "Problema"))
			)
	})
	ResponseEntity<Void> remover(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long id);

}
