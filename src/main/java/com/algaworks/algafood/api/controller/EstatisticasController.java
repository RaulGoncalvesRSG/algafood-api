package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.VendaQueryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

	@Autowired
	private VendaQueryService vendaQueryService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public List<VendaDiariaDTO> consultarVendasDiarias(VendaDiariaFilter filtro,
													   @RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {		//Default com data no UTF
		return vendaQueryService.consultarVendasDiarias(filtro, timeOffset);
	}
	
}
