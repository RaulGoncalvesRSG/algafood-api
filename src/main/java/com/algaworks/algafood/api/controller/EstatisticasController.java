package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import com.algaworks.algafood.core.validation.annotations.Offset;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/estatisticas")
public class EstatisticasController {

	private final VendaQueryService vendaQueryService;
	private final VendaReportService vendaReportService;
	
	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<VendaDiariaDTO>> consultarVendasDiarias(VendaDiariaFilter filtro,
																	   @RequestParam(required = false, defaultValue = "+00:00") @Offset String timeOffset) {		//Default com data no UTC
		return ResponseEntity.ok(vendaQueryService.consultarVendasDiarias(filtro, timeOffset));
	}

	@GetMapping(path = "/vendas-diarias", produces = MediaType.APPLICATION_PDF_VALUE)
	public ResponseEntity<byte[]> consultarVendasDiariasPdf(VendaDiariaFilter filtro,
															@RequestParam(required = false, defaultValue = "+00:00") String timeOffset) {
		byte[] bytesPdf = vendaReportService.emitirVendasDiarias(filtro, timeOffset);

		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=vendas-diarias.pdf");   //"attachment" indica que o conteúdo é para download e não ser mostrado automaticamente (in-line na aba do navegador)
		//headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");			//PDF de forma inline não pode especificar o nome do arquivo

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}
}
