package com.algaworks.algafood.api.converter;

import org.modelmapper.ModelMapper;

import java.lang.reflect.ParameterizedType;

public interface DTOConverter<DTO, OBJ> {

    ModelMapper modelMapper = new ModelMapper();

    default DTO converterToDTO(OBJ obj) {
        return modelMapper.map(obj, ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0]);
    }

    default OBJ convert(DTO dto) {
        return modelMapper.map(dto, ((ParameterizedType) getClass().getGenericInterfaces()[0]).getActualTypeArguments()[1]);
    }
}
