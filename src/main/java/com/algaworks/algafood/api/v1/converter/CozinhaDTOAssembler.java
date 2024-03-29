package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.controller.CozinhaController;
import com.algaworks.algafood.api.v1.converter.generic.ObjectRepresentationDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.response.CozinhaDTO;
import com.algaworks.algafood.domain.model.Cozinha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CozinhaDTOAssembler extends ObjectRepresentationDTOGenericConverter<CozinhaDTO, Cozinha, CozinhaController> {

    @Autowired
    private AlgaLinks algaLinks;

    private static final String COZINHAS = "cozinhas";

    public CozinhaDTOAssembler() {
        super(CozinhaController.class, CozinhaDTO.class);
    }

    @Override
    public CozinhaDTO toModel(Cozinha cozinha) {
        CozinhaDTO dto = createModelWithId(cozinha.getId(), cozinha);
        modelMapper.map(cozinha, dto);

        dto.add(algaLinks.linkToCozinhas(COZINHAS));

        return dto;
    }
}
