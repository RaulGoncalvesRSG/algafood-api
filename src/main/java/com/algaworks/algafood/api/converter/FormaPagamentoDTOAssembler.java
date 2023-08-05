package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.dto.response.FormaPagamentoDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormaPagamentoDTOAssembler extends ObjectDTOGenericConverter<FormaPagamentoDTO, FormaPagamento> {

}
