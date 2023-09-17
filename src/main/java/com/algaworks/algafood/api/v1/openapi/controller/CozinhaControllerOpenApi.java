package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.CozinhaRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.CozinhaDTO;
import com.algaworks.algafood.core.springdoc.PageableParameter;
import com.algaworks.algafood.domain.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;

@SecurityRequirement(name = Constants.SECURITY_SCHEME_NAME)
@Tag(name = Constants.TAG_COZINHA)
public interface CozinhaControllerOpenApi {

	@PageableParameter
	@Operation(summary = "Lista as cozinhas com paginação")
	ResponseEntity<PagedModel<CozinhaDTO>> listar(@Parameter(hidden = true) Pageable pageable);

	@Operation(summary = "Busca uma cozinha por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da cozinha inválido",
					content = @Content(schema = @Schema(ref = "Problema"))),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
					content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<CozinhaDTO> buscar(@Parameter(description = "ID de uma cozinha", example = "1", required = true) Long id);

	@Operation(summary = "Cadastra uma cozinha", responses = {
			@ApiResponse(responseCode = "201", description = "Cozinha cadastrada"),
	})
	ResponseEntity<CozinhaDTO> adicionar(@RequestBody(description = "Representação de uma nova cozinha", required = true) CozinhaRequestDTO requestDTO);

	@Operation(summary = "Atualiza uma cozinha por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Cozinha atualizada"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
					content = @Content(schema = @Schema(ref = "Problema"))),
	})
	ResponseEntity<CozinhaDTO> atualizar(
			@Parameter(description = "ID de uma cozinha", example = "1", required = true) Long id,
			@RequestBody(description = "Representação de uma cozinha com os novos dados", required = true) CozinhaRequestDTO requestDTO);

	@Operation(summary = "Exclui uma cozinha por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Cozinha excluída"),
			@ApiResponse(responseCode = "404", description = "Cozinha não encontrada",
					content = @Content(schema = @Schema(ref = "Problema")))
	})
	ResponseEntity<Void> remover(@Parameter(description = "ID de uma cozinha", example = "1", required = true) Long id);

}
