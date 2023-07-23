package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.FotoProdutoDTOAssembler;
import com.algaworks.algafood.api.dto.request.FotoProdutoRequestDTO;
import com.algaworks.algafood.api.dto.response.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController {

    private final ProdutoService cadastroProduto;
    private final CatalogoFotoProdutoService catalogoFotoProduto;
    private final FotoProdutoDTOAssembler fotoProdutoDTOAssembler;

    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoDTO> atualizarFoto(@PathVariable Long restauranteId,
                                                        @PathVariable Long produtoId,
                                                        @Valid FotoProdutoRequestDTO fotoProdutoInput) throws IOException {
        Produto produto = cadastroProduto.buscarOuFalhar(restauranteId, produtoId);
        MultipartFile arquivo = fotoProdutoInput.getArquivo();
        FotoProduto foto = catalogoFotoProduto.buildFotoProduto(produto, fotoProdutoInput, arquivo);
        FotoProdutoDTO dto = catalogoFotoProduto.salvar(foto, arquivo.getInputStream());
     //   FotoProdutoDTO dto = fotoProdutoDTOAssembler.toDTO(fotoSalva);
        return ResponseEntity.ok(dto);
    }
}
