package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.converter.GrupoRequestDTODisassembler;
import com.algaworks.algafood.api.v1.dto.request.GrupoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.GrupoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoControllerOpenApi;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.service.GrupoService;
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
@RequestMapping("/grupos")
public class GrupoController implements GrupoControllerOpenApi {

    private final GrupoService service;
    private final GrupoDTOAssembler assembler;
    private final GrupoRequestDTODisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<GrupoDTO>> listar(){
        List<Grupo> grupos = service.listar();
        List<GrupoDTO> dtos = assembler.toCollectionDTO(grupos);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GrupoDTO> buscar(@PathVariable Long id){
        Grupo grupo = service.buscarOuFalhar(id);
        GrupoDTO dto = assembler.toDTO(grupo);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<GrupoDTO> adicionar(@RequestBody @Valid GrupoRequestDTO requestDTO){
        Grupo grupo = disassembler.toDomainObject(requestDTO);
        grupo = service.salvar(grupo);
        GrupoDTO dto = assembler.toDTO(grupo);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<GrupoDTO> atualizar(@PathVariable Long id, @RequestBody  @Valid GrupoRequestDTO requestDTO) {
        Grupo grupoAtual = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(requestDTO, grupoAtual);
        grupoAtual = service.salvar(grupoAtual);
        GrupoDTO dto = assembler.toDTO(grupoAtual);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
