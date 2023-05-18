package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class EstadoRequestDTODisassembler {

    private final ModelMapper modelMapper;

    public Estado toDomainObject(EstadoRequestDTO dto){
        return modelMapper.map(dto, Estado.class);
    }

    public void copyToDomainObject(EstadoRequestDTO dto, Estado estado) {
        modelMapper.map(dto, estado);
    }
}
