package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.request.FormaPagamentoRequestDTO;
import com.algaworks.algafood.domain.model.FormaPagamento;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FormaPagamentoRequestDTODisassembler extends ObjectDTOGenericConverter<FormaPagamentoRequestDTO, FormaPagamento> {

}
