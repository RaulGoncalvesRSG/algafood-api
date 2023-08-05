package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.UsuarioDTOAssembler;
import com.algaworks.algafood.api.dto.response.UsuarioDTO;
import com.algaworks.algafood.api.openapi.controller.RestauranteUsuarioResponsavelControllerOpenApi;
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

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController implements RestauranteUsuarioResponsavelControllerOpenApi {

    private final RestauranteService service;
    private final UsuarioDTOAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>> listar(@PathVariable Long restauranteId){
        Restaurante restaurante = service.buscarOuFalhar(restauranteId);
        CollectionModel<UsuarioDTO> dtos = assembler.toCollectionModel(restaurante.getUsuarios());

        //toCollectionModel retorna a coleção com self href de link de usuários. Então é preciso remover o link e add um correto
        dtos.removeLinks().add(linkTo(methodOn(RestauranteUsuarioResponsavelController.class)
                        .listar(restauranteId)).withSelfRel());

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
