package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.CidadeRequestDTO;
import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.model.Estado;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class CidadeRequestDTODisassembler {

    private final ModelMapper modelMapper;

    public Cidade toDomainObject(CidadeRequestDTO dto){
        return modelMapper.map(dto, Cidade.class);
    }

    public void copyToDomainObject(CidadeRequestDTO dto, Cidade cidade) {
        cidade.setEstado(new Estado());
        modelMapper.map(dto, cidade);
    }
}
