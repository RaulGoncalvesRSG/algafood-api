package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    void armazenar(NovaFoto novaFoto);       //Serviço de armazenamento de fotos, independente do dispotivio (local, BD, AWS)

    void remover(String nomeArquivo);

    //Métodos default (Default Methods) não precisam ser implementados por quem implementar a interface
    default void substituir(String nomeArquivoAntigo, NovaFoto novaFoto) {
        this.armazenar(novaFoto);

        if (nomeArquivoAntigo != null){
            this.remover(nomeArquivoAntigo);
        }
    }

    default String gerarNomeArquivo(String nomeOriginal) {
        return UUID.randomUUID() + "_" + nomeOriginal;
    }

    @Builder
    @Getter
    class NovaFoto {

        private String nomeAquivo;
        private InputStream inputStream;
    }
}
