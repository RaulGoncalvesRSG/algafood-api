package com.algaworks.algafood.core.web;

import org.springframework.http.MediaType;

public class AlgaMediaTypes {

	//Qnd cria MediaType costmuizado, por padrão, utiliza "vnd" no path. O accept do header (client) precisará ter o mesmo valor do produces
	public static final String V1_APPLICATION_JSON_VALUE = "application/vnd.algafood.v1+json";
	//Conversão para qnd não quiser Str e sim o MediaType
	public static final MediaType V1_APPLICATION_JSON = MediaType.valueOf(V1_APPLICATION_JSON_VALUE);

	public static final String V2_APPLICATION_JSON_VALUE = "application/vnd.algafood.v2+json";
	public static final MediaType V2_APPLICATION_JSON = MediaType.valueOf(V2_APPLICATION_JSON_VALUE);

}
