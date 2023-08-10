package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.EstadoDTOAssembler;
import com.algaworks.algafood.api.v1.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.EstadoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.EstadoControllerOpenApi;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController implements EstadoControllerOpenApi {

    private final EstadoService service;
    private final EstadoDTOAssembler assembler;

    @GetMapping
    public ResponseEntity<CollectionModel<EstadoDTO>> listar(){
        List<Estado> estados = service.listar();
        CollectionModel<EstadoDTO> dtos = assembler.toCollectionModel(estados);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscar(@PathVariable Long id){
        Estado estado = service.buscarOuFalhar(id);
        EstadoDTO dto = assembler.toDTO(estado);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> adicionar(@RequestBody @Valid EstadoRequestDTO requestDTO){
        Estado estado = assembler.toDomainObject(requestDTO);
        estado = service.salvar(estado);
        EstadoDTO dto = assembler.toDTO(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EstadoRequestDTO requestDTO) {
        Estado estadoAtual = service.buscarOuFalhar(id);
        assembler.copyToDomainObject(requestDTO, estadoAtual);
        estadoAtual = service.salvar(estadoAtual);
        EstadoDTO dto = assembler.toDTO(estadoAtual);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
