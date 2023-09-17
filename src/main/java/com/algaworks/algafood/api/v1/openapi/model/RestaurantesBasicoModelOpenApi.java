package com.algaworks.algafood.api.v1.openapi.model;

import com.algaworks.algafood.api.v1.dto.response.RestauranteBasicoDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import org.springframework.hateoas.Links;

import java.util.List;

@Schema(description = "RestaurantesBasicoModel")
@Data
public class RestaurantesBasicoModelOpenApi {

	private RestaurantesEmbeddedModelOpenApi _embedded;
	private Links _links;

	@Schema(description = "RestaurantesEmbeddedModel")
	@Data
	public class RestaurantesEmbeddedModelOpenApi {

		private List<RestauranteBasicoDTO> restaurantes;
	}

}
