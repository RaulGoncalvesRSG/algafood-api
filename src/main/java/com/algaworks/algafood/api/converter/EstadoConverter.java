package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.api.dto.response.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class EstadoConverter implements DTOConverter<EstadoDTO, Estado> {

    public void copyToDomainObject(EstadoRequestDTO dto, Estado estado) {
        modelMapper.map(dto, estado);
    }

    public Estado toDomainObject(EstadoRequestDTO dto){
        return modelMapper.map(dto, Estado.class);
    }

    public List<EstadoDTO> toCollectionDTO(List<Estado> estados){
        return estados.stream()
                .map(estado -> converterToDTO(estado))
                .collect(Collectors.toList());
    }
}
