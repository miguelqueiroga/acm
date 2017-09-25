package ACM.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
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

import ACM.model.entity.Retirada;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportRetiradasSocio extends HttpServlet {

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
		SimpleDateFormat dateOrigem = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat dateFormatada = new SimpleDateFormat("MMM/yyyy", new DateFormatSymbols(new java.util.Locale ("pt", "BR")));
		
		byte[] bytes = null;
		HashMap parameters = new HashMap();
		JasperPrint reportPrint = null;
		File reportFile = null;
		
		
		try{
			parameters.put("dataInicial", dateFormatada.format(dateOrigem.parse(dataInicial)));
			parameters.put("dataFinal", dateFormatada.format(dateOrigem.parse(dataFinal)));
			parameters.put("socio", socioNome);
			
			List<Retirada> retiradas = getRetiradasPorPeriodoSocio(socioId, dataInicial, dataFinal);
			Collections.sort (retiradas, new Comparator<Retirada>() {  
	            public int compare(Retirada o1, Retirada o2) {  
	                return o1.getVencimento().getVenNome().compareTo(o2.getVencimento().getVenNome());  
	            }  
	        });	
			
			System.out.println("Tamanho final: " + retiradas.size());
			
			JRDataSource jrds = new JRBeanCollectionDataSource(retiradas);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Retiradas_Socio.jasper"));
						
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

	private List<Retirada> getRetiradasPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		List<Retirada> retiradas = new ArrayList<Retirada>();
		String consulta = "";
		try {
			//System.out.println(consulta);
			consulta = "From ACM.model.entity.Retirada as r where r.socio.socId = " + socioId + " AND r.vencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			retiradas = factoryService.getListPorConsulta(consulta);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return retiradas;
	}
}