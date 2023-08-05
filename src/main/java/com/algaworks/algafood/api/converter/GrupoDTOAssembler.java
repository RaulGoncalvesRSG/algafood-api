package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.dto.response.GrupoDTO;
import com.algaworks.algafood.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrupoDTOAssembler extends ObjectDTOGenericConverter<GrupoDTO, Grupo> {

}
