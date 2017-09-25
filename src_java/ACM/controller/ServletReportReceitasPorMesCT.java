package ACM.controller;

import java.io.File;
import java.io.IOException;
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

import ACM.model.entity.Receita;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportReceitasPorMesCT extends HttpServlet {

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
		"despesaPorVencimentoService");
	;

	}

	@SuppressWarnings("unchecked")
	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
		HashMap parameters = new HashMap();
		JasperPrint reportPrint = null;
		File reportFile = null;
		byte[] bytes = null;
		
		System.out.println("Despesas: " + request.getParameter("consulta"));
		
		String consulta = request.getParameter("consulta");
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		
		parameters.put("dataInicial", dataInicial);
		parameters.put("dataFinal", dataFinal);
		
		try{
			Collection<Receita> receitas = factoryService.getListPorConsulta(consulta);
			
			JRDataSource jrds = new JRBeanCollectionDataSource(receitas);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Receitas_Vencimento_CD.jasper"));
						
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
}