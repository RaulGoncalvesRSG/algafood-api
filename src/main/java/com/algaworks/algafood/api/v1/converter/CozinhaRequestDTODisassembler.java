package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.dto.request.CozinhaRequestDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CozinhaRequestDTODisassembler {

    private final ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaRequestDTO dto){
        return modelMapper.map(dto, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaRequestDTO dto, Cozinha cozinha) {
        modelMapper.map(dto, cozinha);
    }
}
