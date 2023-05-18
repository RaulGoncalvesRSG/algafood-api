package com.algaworks.algafood.api.assembler;

import com.algaworks.algafood.api.dto.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
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
