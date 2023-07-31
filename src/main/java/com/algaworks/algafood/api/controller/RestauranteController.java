package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.RestauranteDTOAssembler;
import com.algaworks.algafood.api.converter.RestauranteRequestDTODisassembler;
import com.algaworks.algafood.api.dto.request.RestauranteRequestDTO;
import com.algaworks.algafood.api.dto.response.RestauranteDTO;
import com.algaworks.algafood.api.dto.view.RestaruanteView;
import com.algaworks.algafood.api.openapi.controller.RestauranteControllerOpenApi;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.exception.RestauranteNaoEncontradoException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.fasterxml.jackson.annotation.JsonView;
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
@RequestMapping("/restaurantes")
public class RestauranteController implements RestauranteControllerOpenApi {

    private final RestauranteService service;
    private final RestauranteDTOAssembler assembler;
    private final RestauranteRequestDTODisassembler disassembler;

    @JsonView(RestaruanteView.Resumo.class)     //O JsonView faz retornar apenas os atributos do DTO com @JsonView(Nome_classe_especificado)
    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> listar(){
        List<Restaurante> restaurantes = service.listar();
        List<RestauranteDTO> dtos = assembler.toCollectionDTO(restaurantes);
        return ResponseEntity.ok(dtos);
    }

    @JsonView(RestaruanteView.ApenasNome.class)     //Retorna apenas os canpos com a anotação e a classe indicada
    @GetMapping(params = "projecao=apenas-nome")     //Se passar parâmetros no GET, chama este método
    public ResponseEntity<List<RestauranteDTO>> listarApenasNomes(){
        return listar();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> buscar(@PathVariable Long id){
        Restaurante restaurante = service.buscarOuFalhar(id);
        RestauranteDTO dto = assembler.toDTO(restaurante);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RestauranteDTO> adicionar(@RequestBody @Valid RestauranteRequestDTO requestDTO){
        try {
            Restaurante restaurante = disassembler.toDomainObject(requestDTO);
            restaurante = service.salvar(restaurante);
            RestauranteDTO dto = assembler.toDTO(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteRequestDTO requestDTO){
        try {
            Restaurante restauranteAtual = service.buscarOuFalhar(id);
            disassembler.copyToDomainObject(requestDTO, restauranteAtual);
            restauranteAtual = service.salvar(restauranteAtual);
            RestauranteDTO dto = assembler.toDTO(restauranteAtual);

            return ResponseEntity.ok(dto);
        } catch (EntidadeNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}/ativo")
    public ResponseEntity<Void> ativar(@PathVariable Long restauranteId) {
        service.ativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/inativo")
    public ResponseEntity<Void> inativar(@PathVariable Long restauranteId) {
        service.inativar(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/ativacoes")
    public ResponseEntity<Void> ativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.ativar(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/ativacoes")
    public ResponseEntity<Void> inativarMultiplos(@RequestBody List<Long> restauranteIds) {
        try {
            service.inativar(restauranteIds);
        }catch (RestauranteNaoEncontradoException e){
            throw new NegocioException(e.getMessage(), e);
        }
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/abertura")
    public ResponseEntity<Void> abrir(@PathVariable Long restauranteId) {
        service.abrir(restauranteId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{restauranteId}/fechamento")
    public ResponseEntity<Void> fechar(@PathVariable Long restauranteId) {
        service.fechar(restauranteId);
        return ResponseEntity.noContent().build();
    }
}
