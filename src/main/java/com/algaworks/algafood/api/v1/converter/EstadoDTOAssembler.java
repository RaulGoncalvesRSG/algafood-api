package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.EstadoController;
import com.algaworks.algafood.api.v1.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.request.EstadoRequestDTO;
import com.algaworks.algafood.api.v1.dto.response.EstadoDTO;
import com.algaworks.algafood.domain.model.Estado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class EstadoDTOAssembler extends ObjectRepresentationDTOGenericConverter<EstadoDTO, Estado, EstadoController> {

    @Autowired
    private AlgaLinks algaLinks;

    private static final String ESTADOS = "estados";

    public EstadoDTOAssembler() {
        super(EstadoController.class, EstadoDTO.class);
    }

    @Override
    public EstadoDTO toModel(Estado estado){
        EstadoDTO dto = createModelWithId(estado.getId(), estado);
        modelMapper.map(estado, dto);

        dto.add(algaLinks.linkToEstados(ESTADOS));

        return dto;
    }

    public void copyToDomainObject(EstadoRequestDTO dto, Estado estado) {
        modelMapper.map(dto, estado);
    }

    public Estado toDomainObject(EstadoRequestDTO dto){
        return modelMapper.map(dto, Estado.class);
    }
}
