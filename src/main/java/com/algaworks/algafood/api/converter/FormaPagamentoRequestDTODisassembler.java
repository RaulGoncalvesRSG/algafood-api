package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.request.FormaPagamentoRequestDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormaPagamentoRequestDTODisassembler {

    private final ModelMapper modelMapper;

    public FormaPagamento toDomainObject(FormaPagamentoRequestDTO dto){
        return modelMapper.map(dto, FormaPagamento.class);
    }

    public void copyToDomainObject(FormaPagamentoRequestDTO dto, FormaPagamento formaPagamento) {
        modelMapper.map(dto, formaPagamento);
    }
}
