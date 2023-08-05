package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.CozinhaController;
import com.algaworks.algafood.api.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.dto.response.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component
public class CozinhaDTOAssembler extends ObjectRepresentationDTOGenericConverter<CozinhaDTO, Cozinha, CozinhaController> {

    private final Class<CozinhaController> controllerClass;

    private static final String COZINHAS = "cozinhas";

    public CozinhaDTOAssembler() {
        super(CozinhaController.class, CozinhaDTO.class);
        this.controllerClass = CozinhaController.class;
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO dto = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, dto);

        dto.add(linkTo(controllerClass)
                .withRel(COZINHAS));

        return dto;
    }
}
