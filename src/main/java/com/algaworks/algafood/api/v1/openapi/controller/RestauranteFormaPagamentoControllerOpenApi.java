package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.response.FormaPagamentoDTO;
import com.algaworks.algafood.domain.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = Constants.SECURITY_SCHEME_NAME)
@Tag(name = Constants.TAG_RESTAURANTE)
public interface RestauranteFormaPagamentoControllerOpenApi {

	@Operation(summary = "Lista as formas de pagamento associadas a restaurante", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<List<FormaPagamentoDTO>> listar(@Parameter(description = "ID do restaurante", example = "1", required = true) Long id);

	@Operation(summary = "Associação de restaurante com forma de pagamento", responses = {
			@ApiResponse(responseCode = "204", description = "Associação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> associar(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

	@Operation(summary = "Desassociação de restaurante com forma de pagamento", responses = {
			@ApiResponse(responseCode = "204", description = "Desassociação realizada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante ou forma de pagamento não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> desassociar(
			@Parameter(description = "ID do restaurante", example = "1", required = true) Long restauranteId,
			@Parameter(description = "ID da forma de pagamento", example = "1", required = true) Long formaPagamentoId);

}