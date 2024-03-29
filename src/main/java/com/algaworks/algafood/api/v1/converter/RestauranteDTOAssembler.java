package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.dto.response.RestauranteDTO;
import com.algaworks.algafood.domain.model.Restaurante;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RestauranteDTOAssembler {

    private final ModelMapper modelMapper;

    public RestauranteDTO toDTO(Restaurante restaurante) {
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> toCollectionDTO(List<Restaurante> restaurantes){
        return restaurantes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
