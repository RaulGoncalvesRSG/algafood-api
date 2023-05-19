package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EstadoRequestDTODisassembler {

    private final ModelMapper modelMapper;

    public Estado toDomainObject(EstadoRequestDTO dto){
        return modelMapper.map(dto, Estado.class);
    }

    public void copyToDomainObject(EstadoRequestDTO dto, Estado estado) {
        modelMapper.map(dto, estado);
    }
}
