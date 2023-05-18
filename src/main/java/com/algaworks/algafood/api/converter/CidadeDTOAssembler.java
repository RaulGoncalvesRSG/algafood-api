package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.CidadeDTO;
import com.algaworks.algafood.domain.model.Cidade;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class CidadeDTOAssembler {

    private final ModelMapper modelMapper;

    public CidadeDTO toDTO(Cidade cidade) {
        return modelMapper.map(cidade, CidadeDTO.class);
    }

    public List<CidadeDTO> toCollectionDTO(List<Cidade> cidades){
        return cidades.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
