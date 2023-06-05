package com.algaworks.algafood.api.converter;

import com.algaworks.algafood.api.dto.response.ProdutoDTO;
import com.algaworks.algafood.domain.model.Produto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProdutoDTOAssembler extends ObjectDTOGenericConverter<ProdutoDTO, Produto> {

}