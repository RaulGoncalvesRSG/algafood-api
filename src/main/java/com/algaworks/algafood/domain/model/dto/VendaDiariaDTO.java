package com.algaworks.algafood.domain.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VendaDiariaDTO {

	private Date data;
	private Long totalVendas;
	private BigDecimal totalFaturado;
}
