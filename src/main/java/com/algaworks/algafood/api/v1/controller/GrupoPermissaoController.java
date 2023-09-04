package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.PermissaoDTOAssembler;
import com.algaworks.algafood.api.v1.dto.response.PermissaoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.GrupoPermissaoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Permissao;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.PermissaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController implements GrupoPermissaoControllerOpenApi {

    private final PermissaoService permissaoService;
    private final GrupoService grupoService;
    private final PermissaoDTOAssembler assembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public ResponseEntity<List<PermissaoDTO>> listar(@PathVariable Long grupoId){
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        List<PermissaoDTO> dtos = assembler.toCollectionDTO(grupo.getPermissoes());
        return ResponseEntity.ok(dtos);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @PutMapping("/{permissaoId}")
    public ResponseEntity<Void> associar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);

        grupoService.associarPermissao(grupo, permissao);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @DeleteMapping("/{permissaoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);
        Permissao permissao = permissaoService.buscarOuFalhar(permissaoId);

        grupoService.desassociarPermissao(grupo, permissao);

        return ResponseEntity.noContent().build();
    }
}
