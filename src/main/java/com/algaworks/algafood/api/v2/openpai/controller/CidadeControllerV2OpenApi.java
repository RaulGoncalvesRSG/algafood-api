package com.algaworks.algafood.api.v2.openpai.controller;

import com.algaworks.algafood.api.exceptionhandler.Problem;
import com.algaworks.algafood.api.v2.dto.request.CidadeRequestDTOV2;
import com.algaworks.algafood.api.v2.dto.response.CidadeDTOV2;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Api(tags = "Cidades")
public interface CidadeControllerV2OpenApi {

    @ApiOperation("Lista as cidades")
    ResponseEntity<CollectionModel<CidadeDTOV2>> listar();

    @ApiOperation("Busca uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 400, message = "ID da cidade inválido", response = Problem.class),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
    ResponseEntity<CidadeDTOV2> buscar(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);

    @ApiOperation("Cadastra uma cidade")
    @ApiResponses({@ApiResponse(code = 201, message = "Cidade cadastrada")})
    ResponseEntity<CidadeDTOV2> adicionar(@ApiParam(name = "corpo", value = "Representação de uma nova cidade", required = true) CidadeRequestDTOV2 requestDTO);

    @ApiOperation("Atualiza uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Cidade atualizada"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
    ResponseEntity<CidadeDTOV2> atualizar(
            @ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId,
            @ApiParam(name = "corpo", value = "Representação de uma cidade com os novos dados", required = true) CidadeRequestDTOV2 requestDTO);

    @ApiOperation("Exclui uma cidade por ID")
    @ApiResponses({
            @ApiResponse(code = 204, message = "Cidade excluída"),
            @ApiResponse(code = 404, message = "Cidade não encontrada", response = Problem.class)})
    ResponseEntity<Void> remover(@ApiParam(value = "ID de uma cidade", example = "1", required = true) Long cidadeId);
}
