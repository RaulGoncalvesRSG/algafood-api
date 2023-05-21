package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormaPagamentoDTOAssembler {

    private final ModelMapper modelMapper;

    public FormaPagamentoDTO toDTO(FormaPagamento formaPagamento) {
        return modelMapper.map(formaPagamento, FormaPagamentoDTO.class);
    }

    public List<FormaPagamentoDTO> toCollectionDTO(List<FormaPagamento> formaPagamentos){
        return formaPagamentos.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
