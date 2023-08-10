package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.response.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissaoDTOAssembler extends ObjectDTOGenericConverter<PermissaoDTO, Permissao> {

}
