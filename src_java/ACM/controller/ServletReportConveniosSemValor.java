package ACM.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

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

import ACM.model.entity.Convenio;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportConveniosSemValor extends HttpServlet {

	private ServletContext sc;
	
	private ConsultaService factoryService = new ConsultaServiceImpl();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		sc = config.getServletContext();
		
		WebApplicationContext webApplicationContext = WebApplicationContextUtils
				.getWebApplicationContext(sc);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext
				.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(factoryService,
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
		Boolean imprimirReceitas = Boolean.TRUE;
		SimpleDateFormat dateOrigem = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat dateFormatada = new SimpleDateFormat("MMM/yyyy", new DateFormatSymbols(new java.util.Locale ("pt", "BR")));
		
		
		byte[] bytes = null;
		HashMap parameters = new HashMap();
		JasperPrint reportPrint = null;
		File reportFile = null;
		String nomeRelatorio = "WEB-INF/relatorios/Relatorio_Convenios_Sem_Valor.jasper";
		
		
		try{
			parameters.put("dataInicial", dateFormatada.format(dateOrigem.parse(dataInicial)));
			parameters.put("dataFinal", dateFormatada.format(dateOrigem.parse(dataFinal)));
			if (socioId != null && !socioId.equals("") && !socioId.equals("NaN")) {
				parameters.put("socia", socioNome);
			}	
			
			Collection<Convenio> convenios = getListaConveniosSemValorPorSocia(socioId, dataInicial, dataFinal);
			JRDataSource jrds = new JRBeanCollectionDataSource(convenios);
			reportFile = new File(sc.getRealPath(nomeRelatorio));
						
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

	private Collection<Convenio> getListaConveniosSemValorPorSocia(String socioId, String dataInicial, String dataFinal) {
		Collection<Convenio> convenios = new ArrayList<Convenio>();
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Convenio as c where c.conId != 2 AND c.conId != 8 AND not exists (select r.rpcConvenio from ACM.model.entity.ReceitaPorConvenio r where r.rpcConvenio = c AND r.rpcVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "')";
			if(socioId != null && !socioId.equals("") && !socioId.equals("NaN")) {
				consulta+= " AND c.conContaPagamento.cpSocio = " + socioId; 
			}
			convenios = factoryService.getListPorConsulta(consulta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convenios;
	}
}