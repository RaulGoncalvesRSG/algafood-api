package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FormaPagamentoDTOAssembler;
import com.algaworks.algafood.api.converter.FormaPagamentoRequestDTODisassembler;
import com.algaworks.algafood.api.dto.request.FormaPagamentoRequestDTO;
import com.algaworks.algafood.api.dto.response.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import com.algaworks.algafood.domain.service.FormaPagamentoService;
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
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private final FormaPagamentoService service;
    private final FormaPagamentoDTOAssembler assembler;
    private final FormaPagamentoRequestDTODisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<FormaPagamentoDTO>> listar(){
        List<FormaPagamento> formaPagamentos = service.listar();
        List<FormaPagamentoDTO> dtos = assembler.toCollectionDTO(formaPagamentos);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> buscar(@PathVariable Long id){
        FormaPagamento formaPagamento = service.buscarOuFalhar(id);
        FormaPagamentoDTO dto = assembler.toDTO(formaPagamento);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<FormaPagamentoDTO> adicionar(@RequestBody @Valid FormaPagamentoRequestDTO requestDTO){
        FormaPagamento formaPagamento = disassembler.toDomainObject(requestDTO);
        formaPagamento = service.salvar(formaPagamento);
        FormaPagamentoDTO dto = assembler.toDTO(formaPagamento);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<FormaPagamentoDTO> atualizar(@PathVariable Long id, @RequestBody  @Valid FormaPagamentoRequestDTO requestDTO) {
        FormaPagamento formaPagamentoAtual = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(requestDTO, formaPagamentoAtual);
        formaPagamentoAtual = service.salvar(formaPagamentoAtual);
        FormaPagamentoDTO dto = assembler.toDTO(formaPagamentoAtual);

        return ResponseEntity.ok(dto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> remover(@PathVariable Long id) {
        service.excluir(id);
        return ResponseEntity.noContent().build();
    }
}
