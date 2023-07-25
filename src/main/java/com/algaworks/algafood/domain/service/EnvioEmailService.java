package com.algaworks.algafood.domain.service;

import lombok.Builder;
import lombok.Getter;
import lombok.NonNull;
import lombok.Singular;

import java.util.Map;
import java.util.Set;

public interface EnvioEmailService {

    void enviar(Mensagem mensagem);

    @Getter
    @Builder
    class Mensagem {
        /*@Singular singulariza a coleção, ou seja, tira o 's' do nome do atributo e permite o parâmetro de um único obj no build
        Para colocar mais de um obj, pode usar: destinatario(obj1).destinatario(obj2)...O segundo não substitui o primeiro*/
        @Singular
        private Set<String> destinatarios;

        @NonNull
        private String assunto;

        @NonNull
        private String corpo;       //Aceita formatação em html

        @Singular("variavel")           //Especifica o nome em singular
        private Map<String, Object> variaveis;
    }
}
