package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstadoDTOAssembler {

    private final ModelMapper modelMapper;

    public EstadoDTO toDTO(Estado estado) {
        return modelMapper.map(estado, EstadoDTO.class);
    }

    public List<EstadoDTO> toCollectionDTO(List<Estado> estados){
        return estados.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
