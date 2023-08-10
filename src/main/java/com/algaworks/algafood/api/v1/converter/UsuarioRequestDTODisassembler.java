package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.request.UsuarioRequestDTO;
import com.algaworks.algafood.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioRequestDTODisassembler extends ObjectDTOGenericConverter<UsuarioRequestDTO, Usuario> {

}
