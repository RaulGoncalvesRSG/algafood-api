package com.algaworks.algafood.api.v1.openapi.controller;

import com.algaworks.algafood.api.v1.dto.request.SenhaRequestDTO;
import com.algaworks.algafood.api.v1.dto.request.UsuarioComSenhaResquestDTO;
import com.algaworks.algafood.api.v1.dto.request.UsuarioRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
import com.algaworks.algafood.api.exceptionhandler.Problem;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Usuários")
public interface UsuarioControllerOpenApi {

	@ApiOperation("Lista os usuários")
	ResponseEntity<CollectionModel<UsuarioDTO>> listar();

	@ApiOperation("Busca um usuário por ID")
	@ApiResponses({
			@ApiResponse(code = 400, message = "ID do usuário inválido", response = Problem.class),
			@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)})
	ResponseEntity<UsuarioDTO>  buscar(@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId);

	@ApiOperation("Cadastra um usuário")
	@ApiResponses({@ApiResponse(code = 201, message = "Usuário cadastrado")})
	ResponseEntity<UsuarioDTO>  adicionar(@ApiParam(name = "corpo", value = "Representação de um novo usuário", required = true) UsuarioComSenhaResquestDTO resquestDTO);

	@ApiOperation("Atualiza um usuário por ID")
	@ApiResponses({
			@ApiResponse(code = 200, message = "Usuário atualizado"),
			@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)})
	ResponseEntity<UsuarioDTO>  atualizar(
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
			@ApiParam(name = "corpo", value = "Representação de um usuário com os novos dados", required = true) UsuarioRequestDTO resquestDTO);

	@ApiOperation("Atualiza a senha de um usuário")
	@ApiResponses({
			@ApiResponse(code = 204, message = "Senha alterada com sucesso"),
			@ApiResponse(code = 404, message = "Usuário não encontrado", response = Problem.class)})
	ResponseEntity<Void> alterarSenha(
			@ApiParam(value = "ID do usuário", example = "1", required = true) Long usuarioId,
			@ApiParam(name = "corpo", value = "Representação de uma nova senha", required = true) SenhaRequestDTO senhaDTO);
}