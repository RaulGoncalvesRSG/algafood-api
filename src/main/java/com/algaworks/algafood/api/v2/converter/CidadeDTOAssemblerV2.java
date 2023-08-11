package com.algaworks.algafood.api.v2.converter;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v2.controller.CidadeControllerV2;
import com.algaworks.algafood.api.v2.dto.response.CidadeDTOV2;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@Component      //Exemplo de Assembler com Hateoas sem estender classe genérica
public class CidadeDTOAssemblerV2 extends RepresentationModelAssemblerSupport<Cidade, CidadeDTOV2> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    private static final String CIDADES = "cidades";

    public CidadeDTOAssemblerV2() {
        super(CidadeControllerV2.class, CidadeDTOV2.class);
    }

    @Override
    public CidadeDTOV2 toModel(Cidade cidade) {
        CidadeDTOV2 dto = createModelWithId(cidade.getId(), cidade);
        modelMapper.map(cidade, dto);
        return dto;
    }

    @Override
    public CollectionModel<CidadeDTOV2> toCollectionModel(Iterable<? extends Cidade> cidades) {
        return super.toCollectionModel(cidades)
                .add(linkTo(CidadeControllerV2.class).withSelfRel());
    }

    public List<CidadeDTOV2> toCollectionDTO(List<Cidade> cidades){
        return cidades.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
