package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.EstadoDTO;
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

@SecurityRequirement(name = Constants.SECURITY_SCHEME_NAME)
@Tag(name = Constants.TAG_ESTADO)
public interface EstadoControllerOpenApi {

	@Operation(summary = "Lista os estados")
	ResponseEntity<CollectionModel<EstadoDTO>> listar();

	@Operation(summary = "Busca um estado por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do estado inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<EstadoDTO> buscar(@Parameter(description = "ID de um estado", example = "1", required = true) Long id);

	@Operation(summary = "Cadastra um estado", responses = {
			@ApiResponse(responseCode = "201", description = "Estado cadastrado")
	})
	ResponseEntity<EstadoDTO> adicionar(@RequestBody(description = "Representação de um novo estado", required = true) EstadoRequestDTO requestDTO);

	@Operation(summary = "Atualiza um estado por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Estado atualizado"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<EstadoDTO> atualizar(
			@Parameter(description = "ID de um estado", example = "1", required = true) Long id,
			@RequestBody(description = "Representação de um estado com os novos dados", required = true) EstadoRequestDTO requestDTO);

	@Operation(summary = "Exclui um estado por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Estado excluído"),
			@ApiResponse(responseCode = "404", description = "Estado não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<Void> remover(@Parameter(description = "ID de um estado", example = "1", required = true) Long id);

}