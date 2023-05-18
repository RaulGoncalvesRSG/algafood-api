package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.request.CozinhaRequestDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CozinhaRequestDTODisassembler {

    private final ModelMapper modelMapper;

    public Cozinha toDomainObject(CozinhaRequestDTO dto){
        return modelMapper.map(dto, Cozinha.class);
    }

    public void copyToDomainObject(CozinhaRequestDTO dto, Cozinha cozinha) {
        modelMapper.map(dto, cozinha);
    }
}
