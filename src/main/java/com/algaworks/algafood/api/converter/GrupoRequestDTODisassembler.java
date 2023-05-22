package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.GrupoRequestDTO;
import com.algaworks.algafood.domain.model.Grupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GrupoRequestDTODisassembler extends ObjectDTOGenericConverter<GrupoRequestDTO, Grupo> {

}