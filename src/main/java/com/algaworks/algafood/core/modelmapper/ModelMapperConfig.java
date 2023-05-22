package com.algaworks.algafood.core.modelmapper;

import com.algaworks.algafood.api.dto.response.EnderecoDTO;
import com.algaworks.algafood.domain.model.Endereco;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        ModelMapper modelMapper = new ModelMapper();
        //modelMapper.createTypeMap(Restaurante.class, RestauranteModel.class)
//			.addMapping(Restaurante::getTaxaFrete, RestauranteModel::setPrecoFrete);

        //Mapeamento de Endereco para EnderecoDTO
        TypeMap<Endereco, EnderecoDTO> enderecoDTOTypeMap = modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class);
        //getNome n está no Endereco e setEstado n está no EnderecoDTO, então faz o lambda
        enderecoDTOTypeMap.<String>addMapping(
                enderecoSource -> enderecoSource.getCidade().getEstado().getNome(),
                (enderecoDtoDestine, valueSource) -> enderecoDtoDestine.getCidade().setEstado(valueSource));

        return modelMapper;
    }
}
