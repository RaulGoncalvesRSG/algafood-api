package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.api.converter.PermissaoDTOAssembler;
import com.algaworks.algafood.api.dto.response.PermissaoDTO;
import com.algaworks.algafood.api.openapi.controller.PermissaoControllerOpenApi;
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
@RequestMapping("/permissoes")
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
