package com.algaworks.algafood.domain.exception;

import org.springframework.http.MediaType;

import java.util.List;

public class ContentTypeNaoAcessivelException extends EntidadeNaoEncontradaException {

	private static final long serialVersionUID = 1L;

	public ContentTypeNaoAcessivelException(List<MediaType> supportedMediaTypes) {
		super(supportedMediaTypes.toString());
	}
	
}
