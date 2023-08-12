package com.algaworks.algafood.core.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.hateoas.mediatype.hal.HalConfiguration;
import org.springframework.http.MediaType;

@Configuration
public class AlgaHalConfiguration {		//Classe utilizada apenas quando utiliza versionamento por MediaType

	@Bean		//Habilita o formato MediaTypes.HAL_JSON. Informa quais os media types aceitos para o HAL
	public HalConfiguration globalPolicy() {
		return new HalConfiguration()
				.withMediaType(MediaType.APPLICATION_JSON)
				.withMediaType(AlgaMediaTypes.V1_APPLICATION_JSON)
				.withMediaType(AlgaMediaTypes.V2_APPLICATION_JSON);
	}
	
}