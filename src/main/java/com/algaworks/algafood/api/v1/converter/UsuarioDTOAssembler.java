package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.UsuarioController;
import com.algaworks.algafood.api.v1.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.response.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UsuarioDTOAssembler extends ObjectRepresentationDTOGenericConverter<UsuarioDTO, Usuario, UsuarioController> {

    @Autowired
    private AlgaLinks algaLinks;

    private static final String USUARIOS = "usuarios";
    private static final String GRUPOS_USUARIO = "grupos-usuario";

    public UsuarioDTOAssembler() {
        super(UsuarioController.class, UsuarioDTO.class);
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO dto = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, dto);

        dto.add(algaLinks.linkToUsuarios(USUARIOS));
        dto.add(algaLinks.linkToGruposUsuario(usuario.getId(), GRUPOS_USUARIO));

        return dto;
    }
}
