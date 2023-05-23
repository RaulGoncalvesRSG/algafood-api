package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.UsuarioDTO;
import com.algaworks.algafood.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioDTOAssembler extends ObjectDTOGenericConverter<UsuarioDTO, Usuario> {

}
