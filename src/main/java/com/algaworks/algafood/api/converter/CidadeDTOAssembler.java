package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.controller.EstadoController;
import com.algaworks.algafood.api.dto.response.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.reactive.WebFluxLinkBuilder.methodOn;

@Component
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    private static final String CIDADES = "cidades";

    public CidadeDTOAssembler() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Override     //Qnd converter um Domain para DTO, adiciona os links hateoas
    public CidadeDTO toModel(Cidade cidade) {
        CidadeDTO cidadeDTO = createModelWithId(cidade.getId(), cidade);        //Cria um DTO com withSelfReal já implementado
        modelMapper.map(cidade, cidadeDTO);                              //Copia as pripriedades do domain para DTO

        //Forma de add withSelfRel sem utilizar createModelWithId do RepresentationModelAssemblerSupport
//        CidadeDTO cidadeDTO = modelMapper.map(cidade, CidadeDTO.class);
//        cidadeDTO.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(cidadeDTO.getId())).withSelfRel());

        //WebMvcLinkBuilder - construtor de link dinâmico. Ele adiciona protocolo, domínio e porta da aplicação para conseguir formar a URL completa do endpoint
        cidadeDTO.add(WebMvcLinkBuilder
                .linkTo(methodOn(CidadeController.class).listar())
                .withRel(CIDADES));

        cidadeDTO.getEstado().add(WebMvcLinkBuilder
                .linkTo(methodOn(EstadoController.class).buscar(cidade.getEstado().getId()))
                .withSelfRel());

        return cidadeDTO;
    }

    @Override
    public CollectionModel<CidadeDTO> toCollectionModel(Iterable<? extends Cidade> cidades) {
        return super.toCollectionModel(cidades)
                .add(linkTo(CidadeController.class).withSelfRel());     //Link para cada DTO
    }

    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades){
        return cidades.stream()
                .map(this::toModel)
                .collect(Collectors.toList());
    }
}
