package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.dto.request.UsuarioRequestDTO;
import com.algaworks.algafood.domain.model.Usuario;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UsuarioRequestDTODisassembler extends ObjectDTOGenericConverter<UsuarioRequestDTO, Usuario> {

}
