package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.RestauranteDTOAssembler;
import com.algaworks.algafood.api.converter.RestauranteRequestDTODisassembler;
import com.algaworks.algafood.api.dto.request.RestauranteRequestDTO;
import com.algaworks.algafood.api.dto.response.RestauranteDTO;
import com.algaworks.algafood.domain.exception.CozinhaNaoEncontradaException;
import com.algaworks.algafood.domain.exception.NegocioException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.RestauranteService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
public class RestauranteController {

    private final RestauranteService service;
    private final RestauranteDTOAssembler dtoAssembler;
    private final RestauranteRequestDTODisassembler dtoDisassembler;

    @GetMapping
    public ResponseEntity<List<RestauranteDTO>> listar(){
        List<Restaurante> restaurantes = service.listar();
        List<RestauranteDTO> dtos = dtoAssembler.toCollectionDTO(restaurantes);
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RestauranteDTO> buscar(@PathVariable Long id){
        Restaurante restaurante = service.buscarOuFalhar(id);
        RestauranteDTO dto = dtoAssembler.toDTO(restaurante);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<RestauranteDTO> adicionar(@RequestBody @Valid RestauranteRequestDTO requestDTO){
        try {
            Restaurante restaurante = dtoDisassembler.toDomainObject(requestDTO);
            restaurante = service.salvar(restaurante);
            RestauranteDTO dto = dtoAssembler.toDTO(restaurante);

            return ResponseEntity.status(HttpStatus.CREATED).body(dto);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<RestauranteDTO> atualizar(@PathVariable Long id, @RequestBody @Valid RestauranteRequestDTO requestDTO){
        try {
            Restaurante restauranteAtual = service.buscarOuFalhar(id);
            dtoDisassembler.copyToDomainObject(requestDTO, restauranteAtual);
            restauranteAtual = service.salvar(restauranteAtual);
            RestauranteDTO dto = dtoAssembler.toDTO(restauranteAtual);

            return ResponseEntity.ok(dto);
        } catch (CozinhaNaoEncontradaException e) {
            throw new NegocioException(e.getMessage());
        }
    }
}
