package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.converter.UsuarioDTOAssembler;
import com.algaworks.algafood.api.v1.converter.UsuarioRequestDTODisassembler;
import com.algaworks.algafood.api.v1.dto.request.SenhaRequestDTO;
import com.algaworks.algafood.api.v1.dto.request.UsuarioComSenhaResquestDTO;
import com.algaworks.algafood.api.v1.dto.request.UsuarioRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
import com.algaworks.algafood.api.v1.openapi.controller.UsuarioControllerOpenApi;
import com.algaworks.algafood.core.security.CheckSecurity;
import com.algaworks.algafood.domain.model.Usuario;
import com.algaworks.algafood.domain.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/v1/usuarios")
public class UsuarioController implements UsuarioControllerOpenApi {

    private final UsuarioService service;
    private final UsuarioDTOAssembler assembler;
    private final UsuarioRequestDTODisassembler disassembler;

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioDTO>> listar(){
        List<Usuario> usuarios = service.listar();
        CollectionModel<UsuarioDTO> dtos = assembler.toCollectionModel(usuarios);
        return ResponseEntity.ok(dtos);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeConsultar
    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> buscar(@PathVariable Long id) {
        Usuario usuario = service.buscarOuFalhar(id);
        UsuarioDTO dto = assembler.toModel(usuario);
        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UsuarioDTO> adicionar(@RequestBody @Valid UsuarioComSenhaResquestDTO resquestDTO) {
        Usuario usuario = disassembler.toDomainObject(resquestDTO);
        usuario = service.salvar(usuario);
        UsuarioDTO dto = assembler.toDTO(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarUsuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Long id, @RequestBody @Valid UsuarioRequestDTO requestDTO) {
        Usuario usuarioAtual = service.buscarOuFalhar(id);
        disassembler.copyToDomainObject(requestDTO, usuarioAtual);
        usuarioAtual = service.salvar(usuarioAtual);
        UsuarioDTO dto = assembler.toDTO(usuarioAtual);
        return ResponseEntity.ok(dto);
    }

    @CheckSecurity.UsuariosGruposPermissoes.PodeAlterarPropriaSenha
    @PutMapping("/{id}/senha")
    public ResponseEntity<Void> alterarSenha(@PathVariable Long id, @RequestBody @Valid SenhaRequestDTO senhaDTO) {
        service.alterarSenha(id, senhaDTO.getSenhaAtual(), senhaDTO.getNovaSenha());
        return ResponseEntity.noContent().build();
    }
}