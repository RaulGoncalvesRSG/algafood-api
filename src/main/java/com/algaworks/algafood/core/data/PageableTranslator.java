package com.algaworks.algafood.core.data;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class PageableTranslator {

	//Métdo para o client ordernar os campos de acordo com a propriedade do DTO. O método faz a conversão das propriedades para ficarem de acordo com o domain
	public static Pageable translate(Pageable pageable, Map<String, String> fieldsMapping) {
		/*Exemplo de transformação: fieldsMapping recebe nomeCliente: ASC -> cliente.nome: ASC. Se tiver a propriedade nomeCliente, seria o order.getProperty().
		O retorno do stream é cliente.nome: ASC*/
		List<Sort.Order> orders = pageable.getSort().stream()
				.filter(order -> fieldsMapping.containsKey(order.getProperty()))			//Filtra apennas propriedades que realmente existem no mapeamento
				.map(order -> new Sort.Order(order.getDirection(),
						fieldsMapping.get(order.getProperty())))
				.collect(Collectors.toList());

		return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), Sort.by(orders));		//orders são as propriedades convertidas
	}
}
