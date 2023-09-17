package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.RestauranteRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.RestauranteDTO;
import com.algaworks.algafood.api.v1.dto.view.RestaruanteView;
import com.algaworks.algafood.domain.util.Constants;
import com.fasterxml.jackson.annotation.JsonView;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

import java.util.List;

@SecurityRequirement(name = Constants.SECURITY_SCHEME_NAME)
@Tag(name = Constants.TAG_RESTAURANTE)
public interface RestauranteControllerOpenApi {

	/*A documentação do swagger para a operação Lista restaurantes vai ser apenas uma
	allowableValues informa o parâmetro permitido, pode passar "apenas-nome" ou nada
	response = RestauranteBasicoModelOpenApi: informa para o swagger substituir o objeto de retorno do endpoint pelo
	que foi especificado no repsonse. OBS: Isso é feito apenas quando está usando @JsonView*/
//	@ApiOperation(value = "Lista restaurantes", response = RestauranteBasicoModelOpenApi.class)
//	@ApiImplicitParams({
//			@ApiImplicitParam(value = "Nome da projeção de pedidos", allowableValues = "apenas-nome", name = "projecao", paramType = "query", type = "string")})

	@Operation(summary = "Lista restaurantes", parameters = {
			@Parameter(name = "projecao",
					description = "Nome da projeção",
					example = "apenas-nome",
					in = ParameterIn.QUERY,
					required = false
			)
	})
	@JsonView(RestaruanteView.Resumo.class)
	ResponseEntity<List<RestauranteDTO>> listar();

	@Operation(hidden = true)		//hidden = true oculta a @ApiOperation value (swagger antigo)
	ResponseEntity<List<RestauranteDTO>> listarApenasNomes();

	@Operation(summary = "Busca um restaurante por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do restaurante inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<RestauranteDTO> buscar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

	@Operation(summary = "Cadastra um restaurante", responses = {
			@ApiResponse(responseCode = "201", description = "Restaurante cadastrado"),
	})
	ResponseEntity<RestauranteDTO> adicionar(@RequestBody(description = "Representação de um novo restaurante", required = true) RestauranteRequestDTO requestDTO);

	@Operation(summary = "Atualiza um restaurante por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Restaurante atualizado"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<RestauranteDTO> atualizar(
			@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id,
			@RequestBody(description = "Representação de um restaurante com os novos dados", required = true) RestauranteRequestDTO requestDTO);

	@Operation(summary = "Ativa um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante ativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> ativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

	@Operation(summary = "Desativa um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante inativado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> inativar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

	@Operation(summary = "Ativa múltiplos restaurantes", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
	})
	ResponseEntity<Void> ativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@Operation(summary = "Inativa múltiplos restaurantes", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurantes ativados com sucesso"),
	})
	ResponseEntity<Void> inativarMultiplos(@RequestBody(description = "IDs de restaurantes", required = true) List<Long> restauranteIds);

	@Operation(summary = "Abre um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante aberto com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> abrir(@Parameter(description = "ID de um restaurante", example = "1", required = true)  Long id);

	@Operation(summary = "Fecha um restaurante por ID", responses = {
			@ApiResponse(responseCode = "204", description = "Restaurante fechado com sucesso"),
			@ApiResponse(responseCode = "404", description = "Restaurante não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<Void> fechar(@Parameter(description = "ID de um restaurante", example = "1", required = true) Long id);

}
