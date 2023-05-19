package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.CozinhaDTOAssembler;
import com.algaworks.algafood.api.converter.CozinhaRequestDTODisassembler;
import com.algaworks.algafood.api.dto.request.CozinhaRequestDTO;
import com.algaworks.algafood.api.dto.response.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
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
@RequestMapping("/cozinhas")
public class CozinhaController {

    private final CozinhaService service;
    private final CozinhaDTOAssembler assembler;
    private final CozinhaRequestDTODisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<CozinhaDTO>> listar(){
        List<Cozinha> cozinhas = service.listar();
        List<CozinhaDTO> dtos = assembler.toCollectionDTO(cozinhas);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CozinhaDTO> buscar(@PathVariable Long id){
        Cozinha cozinha = service.buscarOuFalhar(id);
        CozinhaDTO dto = assembler.toDTO(cozinha);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<CozinhaDTO> adicionar(@RequestBody @Valid CozinhaRequestDTO requestDTO){
        Cozinha cozinha = disassembler.toDomainObject(requestDTO);
        cozinha = service.salvar(cozinha);
        CozinhaDTO dto = assembler.toDTO(cozinha);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CozinhaDTO> atualizar(@PathVariable Long id, @RequestBody @Valid CozinhaRequestDTO requestDTO) {
        Cozinha cozinhaAtual = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(requestDTO, cozinhaAtual);
        cozinhaAtual = service.salvar(cozinhaAtual);
        CozinhaDTO dto = assembler.toDTO(cozinhaAtual);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
