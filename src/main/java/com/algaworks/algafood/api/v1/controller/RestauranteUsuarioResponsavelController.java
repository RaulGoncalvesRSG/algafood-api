package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.converter.UsuarioDTOAssembler;
import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final RestauranteService service;
    private final UsuarioDTOAssembler assembler;
    private final AlgaLinks algaLinks;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = service.buscarOuFalhar(restauranteId);
        CollectionModel<UsuarioDTO> dtos = assembler.toCollectionModel(restaurante.getUsuarios());

        //assembler.toCollectionModel retorna a coleção com self href de link de usuários. Então é preciso remover o link e add um correto
        dtos.removeLinks().add(algaLinks.linkToRestauranteResponsaveis(restauranteId));

        return ResponseEntity.ok(dtos);
    }

    @PutMapping("/{usuarioId}")
    public ResponseEntity<Void> associar(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        service.associarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{usuarioId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long restauranteId, @PathVariable Long usuarioId){
        service.desassociarResponsavel(restauranteId, usuarioId);
        return ResponseEntity.noContent().build();
    }
}
