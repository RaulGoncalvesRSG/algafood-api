package com.algaworks.algafood.api.v1.dto.request;

import com.algaworks.algafood.core.validation.annotations.FileContentType;
import com.algaworks.algafood.core.validation.annotations.FileSize;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoRequestDTO {

	@Schema(description = "Arquivo da foto do produto (máximo 2MB, apenas JPG e PNG)")
	@NotNull
	@FileSize(max = "5000KB")			//O default do tamanho do MultipartFile é no máximo 1mb. O rquest completa (tds arquivos) com no max 10mb
	@FileContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE }, message = "Somente são permitido os tipos {allowed}")
	private MultipartFile arquivo;

	@Schema(description = "Descrição da foto do produto")
	@NotBlank
	private String descricao;
	
}
