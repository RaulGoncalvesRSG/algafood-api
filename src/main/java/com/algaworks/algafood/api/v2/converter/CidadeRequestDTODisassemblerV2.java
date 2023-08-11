package com.algaworks.algafood.api.v2.converter;

import com.algaworks.algafood.api.v2.dto.request.CidadeRequestDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CidadeRequestDTODisassemblerV2 {

    private final ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeRequestDTOV2 dto){
        return modelMapper.map(dto, Cidade.class);
    }

    public void copyToDomainObject(CidadeRequestDTOV2 dto, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(dto, cidade);
    }
}
