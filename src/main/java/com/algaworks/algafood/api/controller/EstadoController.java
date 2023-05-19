package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.EstadoConverter;
import com.algaworks.algafood.api.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.api.dto.response.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import com.algaworks.algafood.domain.service.EstadoService;
import lombok.RequiredArgsConstructor;
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
public class EstadoController {

    private final EstadoService service;
    private final EstadoConverter estadoConverter;

    @GetMapping
    public ResponseEntity<List<EstadoDTO>> listar(){
        List<Estado> estados = service.listar();
        List<EstadoDTO> dtos = estadoConverter.toCollectionDTO(estados);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstadoDTO> buscar(@PathVariable Long id){
        Estado estado = service.buscarOuFalhar(id);
        EstadoDTO dto = estadoConverter.converterToDTO(estado);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<EstadoDTO> adicionar(@RequestBody @Valid EstadoRequestDTO requestDTO){
        Estado estado = estadoConverter.toDomainObject(requestDTO);
        estado = service.salvar(estado);
        EstadoDTO dto = estadoConverter.converterToDTO(estado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstadoDTO> atualizar(@PathVariable Long id, @RequestBody @Valid EstadoRequestDTO requestDTO) {
        Estado estadoAtual = service.buscarOuFalhar(id);
        estadoConverter.copyToDomainObject(requestDTO, estadoAtual);
        estadoAtual = service.salvar(estadoAtual);
        EstadoDTO dto = estadoConverter.converterToDTO(estadoAtual);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
