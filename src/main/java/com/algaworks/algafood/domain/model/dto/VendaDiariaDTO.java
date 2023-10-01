package com.algaworks.algafood.domain.model.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Setter
@Getter
public class VendaDiariaDTO {

	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;

	/*JPA 3.0 instancia a classe Date do sql e não do util; e o JR n consegue identificar isso
	Date do sql tem limitações como ser transformado em um obj Instant. O JR tenta fazer essa conversão com toInstant e gera exceção*/
	public VendaDiariaDTO(java.sql.Date data, Long totalVendas, BigDecimal totalFaturado) {
		this.data = new Date(data.getTime());	    //Resolução de problema para gerar relatórios com o Jasper Reports. Cria uma instância do util.Date
		this.totalVendas = totalVendas;
		this.totalFaturado = totalFaturado;
	}
}
