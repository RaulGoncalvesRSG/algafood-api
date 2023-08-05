package com.algaworks.algafood.core.jackson;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.springframework.boot.jackson.JsonComponent;
import org.springframework.data.domain.Page;

import java.io.IOException;

@JsonComponent //JsonComponent - Componente q fornece implementação de serializador/desserializador q deve ser registrado no jackson
public class PageJsonSerializer extends JsonSerializer<Page<?>> {  //Page<?> - serializador para qualquer tipo de página

	@Override		//Alterando a serialização da paginação dos endpoints. OBS: A configuração é para tds endpoints do projeto
	public void serialize(Page<?> page, JsonGenerator generator, SerializerProvider serializers) throws IOException {
		generator.writeStartObject();
		//Adicionando as informações desejadas para o Page. O objetivo é simplificar a qtd de atributos apresentadas na paginação
		generator.writeObjectField("content", page.getContent());
		generator.writeNumberField("size", page.getSize());
		generator.writeNumberField("totalElements", page.getTotalElements());
		generator.writeNumberField("totalPages", page.getTotalPages());
		generator.writeNumberField("number", page.getNumber());
		
		generator.writeEndObject();
	}
}
