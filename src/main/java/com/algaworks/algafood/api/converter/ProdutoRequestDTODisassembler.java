package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.dto.request.ProdutoRequestDTO;
import com.algaworks.algafood.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoRequestDTODisassembler extends ObjectDTOGenericConverter<ProdutoRequestDTO, Produto> {

}
