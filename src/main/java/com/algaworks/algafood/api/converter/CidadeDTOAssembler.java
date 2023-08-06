package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.AlgaLinks;
import com.algaworks.algafood.api.controller.CidadeController;
import com.algaworks.algafood.api.dto.response.CidadeDTO;
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
public class CidadeDTOAssembler extends RepresentationModelAssemblerSupport<Cidade, CidadeDTO> {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private AlgaLinks algaLinks;

    private static final String CIDADES = "cidades";

    public CidadeDTOAssembler() {
        super(CidadeController.class, CidadeDTO.class);
    }

    @Override     //Qnd converter um Domain para DTO, adiciona os links hateoas
    public CidadeDTO toModel(Cidade cidade) {
        CidadeDTO dto = createModelWithId(cidade.getId(), cidade);        //Cria um DTO com withSelfReal já implementado
        modelMapper.map(cidade, dto);                              //Copia as pripriedades do domain para DTO

        //Forma de add withSelfRel sem utilizar createModelWithId do RepresentationModelAssemblerSupport
//        CidadeDTO dto = modelMapper.map(cidade, CidadeDTO.class);
//        dto.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CidadeController.class).buscar(dto.getId())).withSelfRel());

        dto.add(algaLinks.linkToCidades(CIDADES));
        dto.getEstado().add(algaLinks.linkToEstado(dto.getEstado().getId()));

        return dto;
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
