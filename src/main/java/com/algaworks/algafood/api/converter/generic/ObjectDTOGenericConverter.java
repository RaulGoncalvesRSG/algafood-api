package com.algaworks.algafood.api.converter.generic;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public abstract class ObjectDTOGenericConverter<DTO, DOMAIN> {

    @Autowired
    protected ModelMapper modelMapper;

    private final Class<DTO> dtoObject;
    private final Class<DOMAIN> domainObject;

    @SuppressWarnings("unchecked")
    public ObjectDTOGenericConverter() {
        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.dtoObject = (Class<DTO>) type.getActualTypeArguments()[0];
        this.domainObject = (Class<DOMAIN>) type.getActualTypeArguments()[1];
    }

    public DTO toDTO(DOMAIN domain) {
        return this.modelMapper.map(domain, dtoObject);
    }

    public DOMAIN toDomainObject(DTO dto) {
        return this.modelMapper.map(dto, domainObject);
    }

    public void copyToDomainObject(DTO dto, DOMAIN domainObject) {
        modelMapper.map(dto, domainObject);
    }

    public List<DTO> toCollectionDTO(Collection<DOMAIN> listOfDomainObjects) {
        return listOfDomainObjects.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}