package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CozinhaRequestAssembler {

    private final ModelMapper modelMapper;

    public CozinhaDTO toDTO(Cozinha cozinha) {
        return modelMapper.map(cozinha, CozinhaDTO.class);
    }

    public List<CozinhaDTO> toCollectionDTO(List<Cozinha> cozinhas){
        return cozinhas.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
