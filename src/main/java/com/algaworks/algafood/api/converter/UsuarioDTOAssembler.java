package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.UsuarioController;
import com.algaworks.algafood.api.controller.UsuarioGrupoController;
import com.algaworks.algafood.api.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.dto.response.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Component
public class UsuarioDTOAssembler extends ObjectRepresentationDTOGenericConverter<UsuarioDTO, Usuario, UsuarioController> {

    private final Class<UsuarioController> controllerClass;

    private static final String USUARIOS = "usuarios";
    private static final String GRUPOS_USUARIO = "grupos-usuario";

    public UsuarioDTOAssembler() {
        super(UsuarioController.class, UsuarioDTO.class);
        this.controllerClass = UsuarioController.class;
    }

    @Override
    public UsuarioDTO toModel(Usuario usuario) {
        UsuarioDTO usuarioDTO = createModelWithId(usuario.getId(), usuario);
        modelMapper.map(usuario, usuarioDTO);

        /*Em métodos que tem parâmetros, é preciso usar o methodOn para que o HATEOAS descubra quem são eles e possa utilizá-los corretamente
        Sem usar o methodOn, estará fazendo referência ao @RequestMapping que está anotado dentro do Controller,
        que dá na mesma do método listar(), já que o listar() não tem nenhum path diferente do @RequestMapping*/
        usuarioDTO.add(linkTo(controllerClass)
                .withRel(USUARIOS));

        usuarioDTO.add(linkTo(methodOn(UsuarioGrupoController.class)
                .listar(usuarioDTO.getId()))
                .withRel(GRUPOS_USUARIO));

        return usuarioDTO;
    }
}
