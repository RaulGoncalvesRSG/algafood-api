package com.algaworks.algafood.domain.exception;

public class EntidadeNaoEncontradaException extends RuntimeException {

    public EntidadeNaoEncontradaException(String messagem){
        super(messagem);
    }
}
