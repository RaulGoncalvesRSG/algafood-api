package com.algaworks.algafood.api.v1.controller;

import com.algaworks.algafood.api.v1.AlgaLinks;
import com.algaworks.algafood.api.v1.openapi.controller.EstatisticasControllerOpenApi;
import com.algaworks.algafood.core.validation.annotations.Offset;
import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/v1/estatisticas")
public class EstatisticasController implements EstatisticasControllerOpenApi {

	private final VendaQueryService vendaQueryService;
	private final VendaReportService vendaReportService;
	private final AlgaLinks algaLinks;

	@GetMapping( produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<EstatisticasModel> estatisticas() {
		EstatisticasModel estatisticasModel = new EstatisticasModel();
		//Algum dia o sistema pode ter vendas-mensais, vendas-anuais, etc. Então add os links em um endpoint
		estatisticasModel.add(algaLinks.linkToEstatisticasVendasDiarias("vendas-diarias"));
		return ResponseEntity.ok(estatisticasModel);
	}
	
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
		//headers.add(HttpHeaders.CONTENT_DISPOSITION, "inline");			//PDF de forma inline não pode especificar o nome do arquivo. A forma inline permite visualizar o arquivo no navegador sem precisar fazer download

		return ResponseEntity.ok()
				.contentType(MediaType.APPLICATION_PDF)
				.headers(headers)
				.body(bytesPdf);
	}

	public static class EstatisticasModel extends RepresentationModel<EstatisticasModel> {
	}
}
