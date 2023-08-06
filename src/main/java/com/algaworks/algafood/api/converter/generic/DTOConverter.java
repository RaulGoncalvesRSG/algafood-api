package com.algaworks.algafood.api.converter.generic;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.stream.Collectors;

//Exemplo de Conversor como interface
public interface DTOConverter<DTO, OBJ> {

    ModelMapper modelMapper = new ModelMapper();

    default DTO converterToDTO(OBJ obj) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
        return modelMapper.map(obj, type.getActualTypeArguments()[0]);
    }

    default OBJ converterToDomain(DTO dto) {
        ParameterizedType type = (ParameterizedType) getClass().getGenericInterfaces()[0];
        return modelMapper.map(dto, type.getActualTypeArguments()[1]);
    }

    default List<DTO> toCollectionDTO(List<OBJ> listOfDomainObjects) {
        return listOfDomainObjects.stream()
                .map(this::converterToDTO)
                .collect(Collectors.toList());
    }
}