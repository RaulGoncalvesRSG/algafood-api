package com.algaworks.algafood.api.converter.generic;

import lombok.Getter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;

import java.lang.reflect.ParameterizedType;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Getter
public abstract class ObjectRepresentationDTOGenericConverter<DTO extends RepresentationModel<DTO>, DOMAIN, C> extends RepresentationModelAssemblerSupport<DOMAIN, DTO> {

    @Autowired
    protected ModelMapper modelMapper;

    private final Class<DTO> dtoObject;
    private final Class<DOMAIN> domainObject;
    private final Class<C> controllerClass;

    @SuppressWarnings("unchecked")
    public ObjectRepresentationDTOGenericConverter(Class<C> controllerClass, Class<DTO> dtoObject) {
        super(controllerClass, dtoObject);

        ParameterizedType type = (ParameterizedType) getClass().getGenericSuperclass();
        this.dtoObject = (Class<DTO>) type.getActualTypeArguments()[0];
        this.domainObject = (Class<DOMAIN>) type.getActualTypeArguments()[1];
        this.controllerClass = controllerClass;
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

    public CollectionModel<DTO> toCollectionModel(Iterable<? extends DOMAIN> entities) {
        return super.toCollectionModel(entities)
                .add(linkTo(controllerClass).withSelfRel());
    }
}