package ACM.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.ResumoAnual;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;
import ACM.model.service.RelatorioService;
import ACM.model.service.RelatorioServiceImpl;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportDistribuicaoAnual extends HttpServlet {

	protected ServletContext sc;
	
	protected RelatorioService factoryService = new RelatorioServiceImpl();

	protected ConsultaService factoryServiceConsulta = new ConsultaServiceImpl();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		sc = config.getServletContext();
		
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(sc);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
				.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(factoryService,
		"relatorioService");
		
		autowireCapableBeanFactory.configureBean(factoryServiceConsulta,
		"consultaService");
	;

	}

	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		String socioId = request.getParameter("socioId");
		String socioNome = request.getParameter("socioNome");
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");

		byte[] bytes = null;
		HashMap parameters = new HashMap();
		JasperPrint reportPrint = null;
		File reportFile = null;		
		
		try{
			List<ResumoAnual> resumos = null;
			List<ReceitaAvulsa> receitasAvulsas = new ArrayList<ReceitaAvulsa>();
			
			if (socioId != null && !socioId.equals("") && !socioId.equals("NaN")) {
				parameters.put("socio", socioNome);
				receitasAvulsas = getReceitasAvulsasPorIntervaloESocia(socioId, dataInicial, dataFinal);
			}
			else {
				parameters.put("socio", "UM");
				socioId = null;
				receitasAvulsas = getReceitasAvulsasPorIntervalo(dataInicial, dataFinal);
			}
			resumos = getListaResumos(socioId, dataInicial, dataFinal, receitasAvulsas);
			
			JRDataSource jrds = new JRBeanCollectionDataSource(resumos);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Demonstrativo_Anual.jasper"));
						
			reportPrint = JasperFillManager.fillReport(reportFile.getAbsolutePath(),  parameters, jrds);
			
			bytes = JasperExportManager.exportReportToPdf(reportPrint);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
				
		if (bytes != null && bytes.length > 0) {
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		}		
	}

	protected List<ResumoAnual> getListaResumos(String socioId, String dataInicial,
			String dataFinal, List<ReceitaAvulsa> receitasAvulsas)
			throws Exception {
		return factoryService.getListResumosAnuais(socioId, dataInicial, dataFinal, receitasAvulsas);
	}

	protected List<ReceitaAvulsa> getReceitasAvulsasPorIntervalo(String dataInicial, String dataFinal) {
		return ReportUtils.getReceitasAvulsasPorIntervalo(dataInicial, dataFinal, factoryServiceConsulta);
	}

	protected List<ReceitaAvulsa> getReceitasAvulsasPorIntervaloESocia(String socioId, String dataInicial,
			String dataFinal) {
		return ReportUtils.getReceitasAvulsasPorIntervaloESocia(socioId, dataInicial, dataFinal, factoryServiceConsulta);
	}

}