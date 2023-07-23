package com.algaworks.algafood.api.dto.request;

import com.algaworks.algafood.core.validation.annotations.FileContentType;
import com.algaworks.algafood.core.validation.annotations.FileSize;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.MediaType;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
@Setter
public class FotoProdutoRequestDTO {

	@NotNull
	@FileSize(max = "5000KB")			//O default do tamanho do MultipartFile é no máximo 1mb. O rquest completa (tds arquivos) com no max 10mb
	@FileContentType(allowed = { MediaType.IMAGE_PNG_VALUE, MediaType.IMAGE_JPEG_VALUE }, message = "Somente são permitido os tipos {allowed}")
	private MultipartFile arquivo;
	
	@NotBlank
	private String descricao;
	
}
