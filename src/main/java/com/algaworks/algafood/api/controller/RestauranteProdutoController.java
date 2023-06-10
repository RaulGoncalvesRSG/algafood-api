package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.ProdutoDTOAssembler;
import com.algaworks.algafood.api.converter.ProdutoRequestDTODisassembler;
import com.algaworks.algafood.api.dto.request.ProdutoRequestDTO;
import com.algaworks.algafood.api.dto.response.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.service.ProdutoService;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos")
public class RestauranteProdutoController {

    private final ProdutoService produtoService;
    private final RestauranteService restauranteService;
    private final ProdutoDTOAssembler assembler;
    private final ProdutoRequestDTODisassembler disassembler;

    @GetMapping
    public ResponseEntity<List<ProdutoDTO>> listar(@PathVariable Long restauranteId,
                                                   @RequestParam(required = false) boolean incluirInativos) {
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        List<Produto> produtos = incluirInativos? produtoService.findTodosByRestaurante(restaurante) : produtoService.findAtivosByRestaurante(restaurante);
        List<ProdutoDTO> dtos = assembler.toCollectionDTO(produtos);

        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{produtoId}")
    public ResponseEntity<ProdutoDTO> buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        ProdutoDTO dto = assembler.toDTO(produto);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ProdutoDTO> salvar(@PathVariable Long restauranteId, @RequestBody ProdutoRequestDTO requestDTO){
        Restaurante restaurante = restauranteService.buscarOuFalhar(restauranteId);

        Produto produto = disassembler.toDomainObject(requestDTO);
        produto.setRestaurante(restaurante);

        produtoService.salvar(produto);         //Ao salvar o produto, é refletido no obj de Restaurante o relecionamento deles, então tbm é salvo no Restaurante
        ProdutoDTO dto = assembler.toDTO(produto);

        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @PutMapping("/{produtoId}")
    public ResponseEntity<ProdutoDTO> atualizar(@PathVariable Long restauranteId, @PathVariable Long produtoId, @RequestBody ProdutoRequestDTO requestDTO){
        Produto produtoAtual = produtoService.buscarOuFalhar(restauranteId, produtoId);

        disassembler.copyToDomainObject(requestDTO, produtoAtual);
        produtoAtual = produtoService.salvar(produtoAtual);
        ProdutoDTO dto = assembler.toDTO(produtoAtual);

        return ResponseEntity.ok(dto);
    }
}
