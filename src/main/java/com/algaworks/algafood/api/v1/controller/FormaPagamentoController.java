package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.v1.converter.FormaPagamentoRequestDTODisassembler;
import com.algaworks.algafood.api.v1.dto.request.FormaPagamentoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.FormaPagamentoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.FormaPagamentoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/formas-pagamento", produces = MediaType.APPLICATION_JSON_VALUE)
public class FormaPagamentoController implements FormaPagamentoControllerOpenApi {

    private final FormaPagamentoService service;
    private final FormaPagamentoDTOAssembler assembler;
    private final FormaPagamentoRequestDTODisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar(){
        List<FormaPagamento> formasPagamento = service.listar();
        List<FormaPagamentoDTO> dtos = assembler.toCollectionDTO(formasPagamento);
        return ResponseEntity.ok(dtos);
    }

    @CheckSecurity.FormasPagamento.PodeConsultar
    @Cacheable(value = "forma-pagamento-buscar")
    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id){
        FormaPagamento formaPagamento = service.buscarOuFalhar(id);
        FormaPagamentoDTO dto = assembler.toDTO(formaPagamento);
        return ResponseEntity.ok(dto);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> adicionar(@RequestBody @Valid FormaPagamentoRequestDTO requestDTO){
        FormaPagamento formaPagamento = disassembler.toDomainObject(requestDTO);
        formaPagamento = service.salvar(formaPagamento);
        FormaPagamentoDTO dto = assembler.toDTO(formaPagamento);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable Long id, @RequestBody  @Valid FormaPagamentoRequestDTO requestDTO) {
        FormaPagamento formaPagamentoAtual = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(requestDTO, formaPagamentoAtual);
        formaPagamentoAtual = service.salvar(formaPagamentoAtual);
        FormaPagamentoDTO dto = assembler.toDTO(formaPagamentoAtual);

        return ResponseEntity.ok(dto);
    }

    @CheckSecurity.FormasPagamento.PodeEditar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
