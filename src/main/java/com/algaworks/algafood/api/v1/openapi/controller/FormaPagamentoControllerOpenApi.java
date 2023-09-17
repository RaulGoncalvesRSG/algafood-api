package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.FormaPagamentoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.FormaPagamentoDTO;
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
@Tag(name = Constants.TAG_FORMA_PAGAMENTO)
public interface FormaPagamentoControllerOpenApi {

	//@ApiResponses({@ApiResponse(code = 200, message = "OK", response = FormasPagamentoModelOpenApi.class)}) OBS: sintaxe do swagger antigo		//Quando usa o ResponseEntity com CollectionModel, o ApiResponse precisa do parâmetro response
	@Operation(summary = "Lista as formas de pagamento")
	ResponseEntity<List<FormaPagamentoDTO>> listar();

	@Operation(summary = "Busca uma forma de pagamento por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID da forma de pagamento inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<FormaPagamentoDTO> buscar(@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long id);

	@Operation(summary = "Cadastra uma forma de pagamento", responses = {
			@ApiResponse(responseCode = "201", description = "Forma de pagamento cadastrada")})
	ResponseEntity<FormaPagamentoDTO> adicionar(@RequestBody(description = "Representação de uma nova forma de pagamento", required = true) FormaPagamentoRequestDTO requestDTO);

	@Operation(summary = "Atualiza uma forma de pagamento por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Forma de pagamento atualizada"),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<FormaPagamentoDTO> atualizar(
			@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true)  Long id,
			@RequestBody(description = "Representação de uma forma de pagamento com os novos dados", required = true) FormaPagamentoRequestDTO requestDTO);

	@Operation(summary = "Exclui uma forma de pagamento por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Forma de pagamento excluída"),
			@ApiResponse(responseCode = "404", description = "Forma de pagamento não encontrada", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<Void> remover(@Parameter(description = "ID de uma forma de pagamento", example = "1", required = true) Long id);

}
