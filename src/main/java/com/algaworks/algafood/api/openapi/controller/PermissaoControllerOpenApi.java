package com.algaworks.algafood.api.openapi.controller;

import com.algaworks.algafood.api.dto.response.PermissaoDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;

import java.util.List;

@Api(tags = "Permissões")
public interface PermissaoControllerOpenApi {

    @ApiOperation("Lista as permissões")
    ResponseEntity<List<PermissaoDTO>> listar();
}
