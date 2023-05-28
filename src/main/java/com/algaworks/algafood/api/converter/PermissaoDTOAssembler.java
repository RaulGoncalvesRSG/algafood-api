package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.PermissaoDTO;
import com.algaworks.algafood.domain.model.Permissao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PermissaoDTOAssembler extends ObjectDTOGenericConverter<PermissaoDTO, Permissao> {

}
