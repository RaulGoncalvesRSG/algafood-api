package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.ResourceUriHelper;
import com.algaworks.algafood.api.v1.converter.CidadeDTOAssembler;
import com.algaworks.algafood.api.v1.converter.CidadeRequestDTODisassembler;
import com.algaworks.algafood.api.v1.dto.request.CidadeRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.CidadeDTO;
import com.algaworks.algafood.api.v1.openapi.controller.CidadeControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
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
@RequestMapping("/v2/cidades")
public class CidadeController implements CidadeControllerOpenApi {

    private final CidadeService service;
    private final CidadeDTOAssembler assembler;
    private final CidadeRequestDTODisassembler disassembler;

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping      //   @GetMapping(produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
    public ResponseEntity<CollectionModel<CidadeDTO>> listar(){
        List<Cidade> cidades = service.listar();
        CollectionModel<CidadeDTO> dtos = assembler.toCollectionModel(cidades);
        return ResponseEntity.ok(dtos);
    }

    @CheckSecurity.Cidades.PodeConsultar
    @GetMapping("/{id}")        // @GetMapping(path = "/{id}", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeDTO> buscar(@PathVariable Long id){
        Cidade cidade = service.buscarOuFalhar(id);
        CidadeDTO dto = assembler.toModel(cidade);
        return ResponseEntity.ok(dto);
    }

    @CheckSecurity.Cidades.PodeEditar
    @PostMapping        //  @PostMapping(produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeDTO> adicionar(@RequestBody @Valid CidadeRequestDTO requestDTO){
        try {
            Cidade cidade = disassembler.toDomainObject(requestDTO);
            cidade = service.salvar(cidade);
            CidadeDTO dto = assembler.toModel(cidade);
            URI uri = ResourceUriHelper.generateURI(dto.getId());

            return ResponseEntity.created(uri).body(dto);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @PutMapping("/{id}")         //   @PutMapping(path = "/{id}", produces = AlgaMediaTypes.V1_APPLICATION_JSON_VALUE)
    public ResponseEntity<CidadeDTO> atualizar(@PathVariable Long id, @RequestBody  @Valid CidadeRequestDTO requestDTO) {
        try {
            Cidade cidadeAtual = service.buscarOuFalhar(id);
            disassembler.copyToDomainObject(requestDTO, cidadeAtual);
            cidadeAtual = service.salvar(cidadeAtual);
            CidadeDTO dto = assembler.toModel(cidadeAtual);

            return ResponseEntity.ok(dto);
        } catch (EstadoNaoEncontradoException e) {
            throw new NegocioException(e.getMessage(), e);
        }
    }

    @CheckSecurity.Cidades.PodeEditar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
