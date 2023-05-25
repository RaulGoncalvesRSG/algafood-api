package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FormaPagamentoDTOAssembler extends ObjectDTOGenericConverter<FormaPagamentoDTO, FormaPagamento> {

    public List<FormaPagamentoDTO> toCollectionDTO(Collection<FormaPagamento> listOfDomainObjects) {
        return listOfDomainObjects.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
