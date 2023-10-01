package com.algaworks.algafood.api.v2.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v2.converter.CidadeDTOAssemblerV2;
import com.algaworks.algafood.api.v2.converter.CidadeRequestDTODisassemblerV2;
import com.algaworks.algafood.api.v2.dto.request.CidadeRequestDTOV2;
import com.algaworks.algafood.api.v2.dto.response.CidadeDTOV2;
import com.algaworks.algafood.api.v2.openpai.controller.CidadeControllerV2OpenApi;
import com.algaworks.algafood.domain.exception.EstadoNaoEncontradoException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.service.CidadeService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.net.URI;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/cidades")
public class CidadeControllerV2 implements CidadeControllerV2OpenApi {

    private final CidadeService service;
    private final CidadeDTOAssemblerV2 assembler;
    private final CidadeRequestDTODisassemblerV2 disassembler;

  //  @GetMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    @GetMapping
    public ResponseEntity<CollectionModel<CidadeDTOV2>> listar(){
        List<Cidade> cidades = service.listar();
        CollectionModel<CidadeDTOV2> dtos = assembler.toCollectionModel(cidades);
        return ResponseEntity.ok(dtos);
    }

  //  @GetMapping(path = "/{id}", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    @GetMapping("/{id}")
    public ResponseEntity<CidadeDTOV2> buscar(@PathVariable Long id){
        Cidade cidade = service.buscarOuFalhar(id);
        CidadeDTOV2 dto = assembler.toModel(cidade);
        return ResponseEntity.ok(dto);
    }

 //   @PostMapping(produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    @PostMapping
    public ResponseEntity<CidadeDTOV2> adicionar(@RequestBody @Valid CidadeRequestDTOV2 requestDTO){
        try {
            Cidade cidade = disassembler.toDomainObject(requestDTO);
            cidade = service.salvar(cidade);
            CidadeDTOV2 dto = assembler.toModel(cidade);
            URI uri = ResourceUriHelper.generateURI(dto.getId());

            return ResponseEntity.created(uri).body(dto);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

  //  @PutMapping(path = "/{id}", produces = AlgaMediaTypes.V2_APPLICATION_JSON_VALUE)
    @PutMapping("/{id}")
    public ResponseEntity<CidadeDTOV2> atualizar(@PathVariable Long id, @RequestBody  @Valid CidadeRequestDTOV2 requestDTO) {
        try {
            Cidade cidadeAtual = service.buscarOuFalhar(id);
            disassembler.copyToDomainObject(requestDTO, cidadeAtual);
            cidadeAtual = service.salvar(cidadeAtual);
            CidadeDTOV2 dto = assembler.toModel(cidadeAtual);

            return ResponseEntity.ok(dto);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

	@DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
