package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.GrupoDTOAssembler;
import com.algaworks.algafood.api.v1.dto.response.GrupoDTO;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioGrupoControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Grupo;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.GrupoService;
import com.algaworks.algafood.domain.service.UsuarioService;
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
@RequestMapping("/v1/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController implements UsuarioGrupoControllerOpenApi {

    private final UsuarioService usuarioService;
    private final GrupoService grupoService;
    private final GrupoDTOAssembler assembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public ResponseEntity<List<GrupoDTO>> listar(@PathVariable Long usuarioId){
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        List<GrupoDTO> dtos = assembler.toCollectionDTO(usuario.getGrupos());
        return ResponseEntity.ok(dtos);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @PutMapping("/{grupoId}")
    public ResponseEntity<Void> associar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuarioService.associarGrupo(usuario, grupo);

        return ResponseEntity.noContent().build();
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeEditar
    @DeleteMapping("/{grupoId}")
    public ResponseEntity<Void> desassociar(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        Usuario usuario = usuarioService.buscarOuFalhar(usuarioId);
        Grupo grupo = grupoService.buscarOuFalhar(grupoId);

        usuarioService.desassociarGrupo(usuario, grupo);

        return ResponseEntity.noContent().build();
    }
}
