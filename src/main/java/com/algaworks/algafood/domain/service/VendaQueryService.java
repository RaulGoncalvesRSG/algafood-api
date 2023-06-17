package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;

import java.util.List;

public interface VendaQueryService {

	List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro, String timeOffset);
	List<VendaDiariaDTO> consultarVendasDiariasJPQL(VendaDiariaFilter filtro);
}
