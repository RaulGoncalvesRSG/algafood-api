package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.PermissaoDTOAssembler;
import com.algaworks.algafood.api.v1.dto.response.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.PermissaoControllerOpenApi;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.repository.PermissaoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/permissoes")
public class PermissaoController implements PermissaoControllerOpenApi {

	private final PermissaoRepository permissaoRepository;
	private final PermissaoDTOAssembler assembler;

	@GetMapping
	public ResponseEntity<List<PermissaoDTO>> listar() {
		List<Permissao> todasPermissoes = permissaoRepository.findAll();
		List<PermissaoDTO> dtos = assembler.toCollectionDTO(todasPermissoes);
		return ResponseEntity.ok(dtos);
	}
}
