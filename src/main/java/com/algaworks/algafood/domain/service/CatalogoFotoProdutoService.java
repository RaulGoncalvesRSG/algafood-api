package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.api.dto.request.FotoProdutoRequestDTO;
import com.algaworks.algafood.api.dto.response.FotoProdutoDTO;
import com.algaworks.algafood.domain.exception.FotoProdutoNaoEncontradoException;
import com.algaworks.algafood.domain.model.FotoProduto;
import com.algaworks.algafood.domain.model.Produto;
import com.algaworks.algafood.domain.repository.ProdutoRepository;
import com.algaworks.algafood.infrasctrure.service.storage.FotoStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CatalogoFotoProdutoService {

    private final ProdutoRepository produtoRepository;
    private final FotoStorageService fotoStorageService;

    public FotoProduto buildFotoProduto(Produto produto, FotoProdutoRequestDTO fotoProdutoDTO, MultipartFile arquivo) {
        return FotoProduto.builder()
                .produto(produto)
                .descricao(fotoProdutoDTO.getDescricao())
                .contentType(arquivo.getContentType())
                .tamanho(arquivo.getSize())
                .nomeArquivo(arquivo.getOriginalFilename())
                .build();
    }

    @Transactional
    public FotoProduto salvar(FotoProduto foto, InputStream dadosArquivo) {
        String nomeNovoArquivo = fotoStorageService.gerarNomeArquivo(foto.getNomeArquivo());
        String nomeArquivoExistente = null;

        Optional<FotoProduto> fotoExistente = produtoRepository.findFotoById(foto.getRestauranteId(), foto.getProduto().getId());

        //Se no momento que for salvar uma foto, já existir alguma, exclui aquela existente para salvar uma por cima
        if (fotoExistente.isPresent()) {
            nomeArquivoExistente = fotoExistente.get().getNomeArquivo();
            produtoRepository.delete(fotoExistente.get());
        }

        /*O flush força ao JPA à descarregar o save para fazer o insert no BD.
        Se for para ocorrer algum erro no save, força o erro antes de salvar a img no disco local
        Se depois q salvar no BD, der erro ao salvar no disco local, faz o rollback*/
        foto.setNomeArquivo(nomeNovoArquivo);
        foto = produtoRepository.save(foto);
        produtoRepository.flush();

        FotoStorageService.NovaFoto novaFoto = FotoStorageService.NovaFoto.builder()
                .filename(foto.getNomeArquivo())
                .contentType(foto.getContentType())
                .size(foto.getTamanho())
                .inputStream(dadosArquivo)
                .build();

        fotoStorageService.substituir(nomeArquivoExistente, novaFoto);
        return foto;
    }

    public FotoProdutoDTO toDTO(FotoProduto fotoProduto) {
        return FotoProdutoDTO.builder()
                .nomeArquivo(fotoProduto.getNomeArquivo())
                .descricao(fotoProduto.getDescricao())
                .contentType(fotoProduto.getContentType())
                .tamanho(fotoProduto.getTamanho())
                .build();
    }

    public FotoProduto buscarOuFalhar(Long restauranteId, Long produtoId) {
        return produtoRepository.findFotoById(restauranteId, produtoId)
                .orElseThrow(() -> new FotoProdutoNaoEncontradoException(restauranteId, produtoId));
    }

    public void verificarCompatibilidadeMediaType(MediaType mediaTypeFoto, List<MediaType> mediaTypesAceitas) throws HttpMediaTypeNotAcceptableException {
        //Se pelo menos um caso der verdadeiro, está compatível
        boolean compativel = mediaTypesAceitas.stream()
                .anyMatch(mediaTypesAceita -> mediaTypesAceita.isCompatibleWith(mediaTypeFoto));

        if (!compativel) {
            throw new HttpMediaTypeNotAcceptableException(mediaTypesAceitas);
        }
    }

    @Transactional
    public void excluir(FotoProduto fotoProduto){
        produtoRepository.delete(fotoProduto);
        produtoRepository.flush();

        fotoStorageService.remover(fotoProduto.getNomeArquivo());
    }
}