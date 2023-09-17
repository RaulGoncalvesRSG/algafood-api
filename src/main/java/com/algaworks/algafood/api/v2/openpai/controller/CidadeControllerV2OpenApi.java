package com.algaworks.algafood.api.v2.openpai.controller;

import com.algaworks.algafood.api.v2.dto.request.CidadeRequestDTOV2;
import com.algaworks.algafood.api.v2.dto.response.CidadeDTOV2;
import com.algaworks.algafood.domain.util.Constants;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;

@Tag(name = Constants.TAG_CIDADE)
public interface CidadeControllerV2OpenApi {

    @Operation(summary = "Lista as cidades")
    ResponseEntity<CollectionModel<CidadeDTOV2>> listar();

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
    ResponseEntity<CidadeDTOV2> buscar(@Parameter(description = "ID de uma cidade", example = "1", required = true) Long id);

    @Operation(summary = "Cadastra uma cidade", description = "Cadastro de uma cidade, " +
            "necessita de um estado e um nome válido")
    ResponseEntity<CidadeDTOV2> adicionar(@RequestBody(description = "Representação de uma nova cidade", required = true)  CidadeRequestDTOV2 requestDTO);

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
    ResponseEntity<CidadeDTOV2> atualizar(
            @Parameter(description = "ID de uma cidade", example = "1", required = true)  Long id,
            @RequestBody(description = "Representação de uma cidade com dados atualizados", required = true) CidadeRequestDTOV2 requestDTO);

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
