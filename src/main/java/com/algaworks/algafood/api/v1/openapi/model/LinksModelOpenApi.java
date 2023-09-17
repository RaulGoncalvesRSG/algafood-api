package com.algaworks.algafood.api.v1.openapi.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Schema(description = "Links")
public class LinksModelOpenApi {

	private LinkModel rel;

	@Setter
	@Getter
	@Schema(description = "Link")
	private class LinkModel {

		private String href;
		private boolean templated;
	}
}
