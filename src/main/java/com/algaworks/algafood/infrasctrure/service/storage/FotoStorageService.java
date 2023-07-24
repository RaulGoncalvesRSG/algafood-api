package com.algaworks.algafood.infrasctrure.service.storage;

import lombok.Builder;
import lombok.Getter;

import java.io.InputStream;
import java.util.UUID;

public interface FotoStorageService {

    FotoRecuperada recuperar(String nomeArquivo);

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

        private String filename;
        private String contentType;
        private InputStream inputStream;
        private Long size;
    }

    @Builder
    @Getter
    class FotoRecuperada {

        private InputStream inputStream;    //Arquivo salvo em disco local utiliza InputStream
        private String url;                 //S3 utilia URL

        public boolean temUrl() {
            return url != null;
        }
    }
}
