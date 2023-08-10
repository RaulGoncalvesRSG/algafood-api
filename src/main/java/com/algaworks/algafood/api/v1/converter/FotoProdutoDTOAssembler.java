package com.algaworks.algafood.api.v1.converter;

import com.algaworks.algafood.api.v1.converter.generic.ObjectDTOGenericConverter;
import com.algaworks.algafood.api.v1.dto.response.FotoProdutoDTO;
import com.algaworks.algafood.domain.model.FotoProduto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FotoProdutoDTOAssembler extends ObjectDTOGenericConverter<FotoProdutoDTO, FotoProduto> {

}
