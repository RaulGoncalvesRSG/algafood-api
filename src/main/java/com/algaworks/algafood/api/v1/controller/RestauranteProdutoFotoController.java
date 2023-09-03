package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.dto.request.FotoProdutoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.FotoProdutoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.RestauranteProdutoFotoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.service.CatalogoFotoProdutoService;
import com.algaworks.algafood.domain.service.ProdutoService;
import com.algaworks.algafood.domain.service.RestauranteService;
import com.algaworks.algafood.infrasctrure.service.storage.FotoStorageService;
import com.algaworks.algafood.infrasctrure.service.storage.FotoStorageService.FotoRecuperada;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/restaurantes/{restauranteId}/produtos/{produtoId}/foto")
public class RestauranteProdutoFotoController implements RestauranteProdutoFotoControllerOpenApi {

    private final ProdutoService produtoService;
    private final CatalogoFotoProdutoService catalogoFotoProdutoService;
    private final FotoStorageService fotoStorageService;
    private final RestauranteService restauranteService;
   // private final FotoProdutoDTOAssembler fotoProdutoDTOAssembler;

    //Parâmetro com @RequestPart MultipartFile, pois o swagger não estava enviando content-type com multipart/form-data ao enviar arquivo
    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @PutMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<FotoProdutoDTO> atualizarFoto(@PathVariable Long restauranteId,
                                                        @PathVariable Long produtoId,
                                                        @Valid FotoProdutoRequestDTO fotoProdutoInput,
                                                        @RequestPart MultipartFile arquivo) throws IOException {
        Produto produto = produtoService.buscarOuFalhar(restauranteId, produtoId);
        FotoProduto foto = catalogoFotoProdutoService.buildFotoProduto(produto, fotoProdutoInput, arquivo);
        FotoProduto fotoSalva = catalogoFotoProdutoService.salvar(foto, arquivo.getInputStream());
        FotoProdutoDTO dto = catalogoFotoProdutoService.toDTO(fotoSalva);
        return ResponseEntity.ok(dto);
    }

    @CheckSecurity.Restaurantes.PodeConsultar
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FotoProdutoDTO> buscar(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        FotoProdutoDTO dto = catalogoFotoProdutoService.toDTO(fotoProduto);
        return ResponseEntity.ok(dto);
    }

    @GetMapping     //O GET é chamado quando coloca qualquer MediaType diferente de aplication/json
    public ResponseEntity<?> exibirFoto(@PathVariable Long restauranteId,
                                                          @PathVariable Long produtoId,
                                                          @RequestHeader(name = "accept") String acceptHeader) throws HttpMediaTypeNotAcceptableException{
        try {
            FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);

            MediaType mediaTypeFoto = MediaType.parseMediaType(fotoProduto.getContentType());
            //O consumidor da API pode passar mais de um tipo de imag (image/png,image/jpeg)
            List<MediaType> mediaTypesAceitas = MediaType.parseMediaTypes(acceptHeader);
            catalogoFotoProdutoService.verificarCompatibilidadeMediaType(mediaTypeFoto, mediaTypesAceitas);

            FotoRecuperada fotoRecuperada = fotoStorageService.recuperar(fotoProduto.getNomeArquivo());
            return buildFotoRecuperada(fotoRecuperada, mediaTypeFoto);
        } catch (EntidadeNaoEncontradaException e){
            return ResponseEntity.notFound().build();
        }
    }

    private ResponseEntity<?> buildFotoRecuperada(FotoRecuperada fotoRecuperada, MediaType mediaTypeFoto){
        if (fotoRecuperada.temUrl()) {
            //Informa para o client (consumidor da API) qual é a URL da img
            return ResponseEntity
                    .status(HttpStatus.FOUND)
                    .header(HttpHeaders.LOCATION, fotoRecuperada.getUrl())
                    .build();
        } else {
            return ResponseEntity.ok()
                    .contentType(mediaTypeFoto)
                    .body(new InputStreamResource(fotoRecuperada.getInputStream()));
        }
    }

    @CheckSecurity.Restaurantes.PodeGerenciarFuncionamento
    @DeleteMapping
    public ResponseEntity<Void> excluir(@PathVariable Long restauranteId, @PathVariable Long produtoId){
        restauranteService.buscarOuFalhar(restauranteId);
        produtoService.buscarOuFalhar(restauranteId, produtoId);

        FotoProduto fotoProduto = catalogoFotoProdutoService.buscarOuFalhar(restauranteId, produtoId);
        catalogoFotoProdutoService.excluir(fotoProduto);

        return ResponseEntity.noContent().build();
    }
}
