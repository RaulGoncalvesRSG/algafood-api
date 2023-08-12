package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.CozinhaDTOAssembler;
import com.algaworks.algafood.api.v1.converter.CozinhaRequestDTODisassembler;
import com.algaworks.algafood.api.v1.dto.request.CozinhaRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.CozinhaDTO;
import com.algaworks.algafood.api.v1.openapi.controller.CozinhaControllerOpenApi;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.service.CozinhaService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/cozinhas")
public class CozinhaController implements CozinhaControllerOpenApi {

    private final CozinhaService service;
    private final CozinhaDTOAssembler assembler;
    private final CozinhaRequestDTODisassembler disassembler;
    private final PagedResourcesAssembler<Cozinha> pagedResourcesAssembler;

    @GetMapping     //PagedModel é um Page que aceita hateoas
    public ResponseEntity<PagedModel<CozinhaDTO>> listar(@PageableDefault(size = 10) Pageable pageable){
        Page<Cozinha> cozinhas = service.listar(pageable);
        /*Usa PagedResourcesAssembler para converter um Page comum para PageModel. Qm faz a conversão de Cozinha (Domain)
        para CozinhaDTO é o CozinhaDTOAssembler, por isso ele é o segundo argumento do método toModel*/
        PagedModel<CozinhaDTO> cozinhasPagedModel = pagedResourcesAssembler.toModel(cozinhas, assembler);

        return ResponseEntity.ok(cozinhasPagedModel);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CozinhaDTO> buscar(@PathVariable Long id){
        Cozinha cozinha = service.buscarOuFalhar(id);
        CozinhaDTO dto = assembler.toModel(cozinha);
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
