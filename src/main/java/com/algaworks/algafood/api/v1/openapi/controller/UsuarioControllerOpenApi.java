package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.SenhaRequestDTO;
import com.algaworks.algafood.api.v1.dto.request.UsuarioComSenhaResquestDTO;
import com.algaworks.algafood.api.v1.dto.request.UsuarioRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
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
@Tag(name = Constants.TAG_USUARIO)
public interface UsuarioControllerOpenApi {

	@Operation(summary = "Lista os usuários")
	ResponseEntity<CollectionModel<UsuarioDTO>> listar();

	@Operation(summary = "Busca um usuário por ID", responses = {
			@ApiResponse(responseCode = "200"),
			@ApiResponse(responseCode = "400", description = "ID do usuário inválido", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) }),
	})
	ResponseEntity<UsuarioDTO> buscar(@Parameter(description = "ID do usuário", example = "1", required = true) Long id);

	@Operation(summary = "Cadastra um usuário", responses = {
			@ApiResponse(responseCode = "201", description = "Usuário cadastrado"),
	})
	ResponseEntity<UsuarioDTO> adicionar(@RequestBody(description = "Representação de um novo usuário", required = true) UsuarioComSenhaResquestDTO resquestDTO);

	@Operation(summary = "Atualiza um usuário por ID", responses = {
			@ApiResponse(responseCode = "200", description = "Usuário atualizado"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<UsuarioDTO> atualizar(
			@Parameter(description = "ID do usuário", example = "1", required = true) Long id,
			@RequestBody(description = "Representação de um usuário com os novos dados", required = true) UsuarioRequestDTO resquestDTO);

	@Operation(summary = "Atualiza a senha de um usuário", responses = {
			@ApiResponse(responseCode = "204", description = "Senha alterada com sucesso"),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = {
					@Content(schema = @Schema(ref = "Problema")) })
	})
	ResponseEntity<Void> alterarSenha(
			@Parameter(description = "ID do usuário", example = "1", required = true) Long usuarioId,
			@RequestBody(description = "Representação de uma nova senha", required = true)SenhaRequestDTO senhaDTO);
}