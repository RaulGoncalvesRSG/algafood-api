package com.algaworks.algafood.infrasctrure.service.report;

import com.algaworks.algafood.domain.filter.VendaDiariaFilter;
import com.algaworks.algafood.domain.model.dto.VendaDiariaDTO;
import com.algaworks.algafood.domain.service.VendaQueryService;
import com.algaworks.algafood.domain.service.VendaReportService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

@RequiredArgsConstructor
@Service
public class PdfVendaReportService implements VendaReportService {

	private final VendaQueryService vendaQueryService;

	private static final String REPORT_LOCALE = "REPORT_LOCALE";		//Nome predefinido para o Jasper Report
	private static final String PATH_RELATORIO = "/relatorios/vendas-diarias.jasper";
	private static final String REPORT_EXCEPTION = "Não foi possível emitir relatório de vendas diárias";

	@Override
	public byte[] emitirVendasDiarias(VendaDiariaFilter filtro, String timeOffset) {
		try {
			InputStream inputStream = this.getClass().getResourceAsStream(PATH_RELATORIO);	 //InputStream é o fluxo de dados do relatório

			HashMap<String, Object> parametros = new HashMap<>();
			parametros.put(REPORT_LOCALE, new Locale("pt", "BR"));

			List<VendaDiariaDTO> vendasDiarias = vendaQueryService.consultarVendasDiarias(filtro, timeOffset);

			//Fonte de dados, de onde vem os dados para preencher o relatório
			JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(vendasDiarias);

			/*JasperPrint representa um relatório preenchido com parâmetros e fonte de dados. Depois de preenchido, ele pode ser exportado em diferentes formatos.
			JasperPrint não tem formato específico (se é PDF, XLS, etc), é apenas um doc preenchido. Para preencher ele precisa de uma fonte de dados*/
			JasperPrint jasperPrint = JasperFillManager.fillReport(inputStream, parametros, dataSource);

			return JasperExportManager.exportReportToPdf(jasperPrint);		//Exporta JasperPrint em formato PDF
		} catch (Exception e) {
			throw new ReportException(REPORT_EXCEPTION, e);
		}
	}
}
