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

import ACM.model.entity.DespesaExtra;
import ACM.model.entity.DespesaPorVencimento;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportDespesasExtras extends HttpServlet {

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
		System.out.println("Despesas: " + request.getParameter("consulta"));
		
		String consulta = request.getParameter("consulta");
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		String departamentos = request.getParameter("departamentos");
		String categorias = request.getParameter("categorias");
		SimpleDateFormat dateOrigem = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat dateFormatada = new SimpleDateFormat("MMM/yyyy", new DateFormatSymbols(new java.util.Locale ("pt", "BR")));
		
		
		byte[] bytes = null;
		HashMap parameters = new HashMap();
		JasperPrint reportPrint = null;
		File reportFile = null;
		
		try{
			parameters.put("dataInicial", dateFormatada.format(dateOrigem.parse(dataInicial)));
			parameters.put("dataFinal", dateFormatada.format(dateOrigem.parse(dataFinal)));
			parameters.put("departamentos", departamentos);
			parameters.put("categorias", categorias);
			
			List<DespesaPorVencimento> despesas = factoryService.getListPorConsulta(consulta);
			
			List<DespesaExtra> despesasExtras = getDespesasExtra(despesas);
			Collections.sort (despesasExtras, new Comparator<DespesaExtra>() {  
	            public int compare(DespesaExtra o1, DespesaExtra o2) {  
	            	if (o1.getDespesaPorVencimento().getDpvVencimento().getVenNome().compareTo(o2.getDespesaPorVencimento().getDpvVencimento().getVenNome()) == 0)
	            		return o1.getDeDescricao().compareTo(o2.getDeDescricao());
	            	else return o1.getDespesaPorVencimento().getDpvVencimento().getVenNome().compareTo(o2.getDespesaPorVencimento().getDpvVencimento().getVenNome());
	            }  	            
	        });			
			
			JRDataSource jrds = new JRBeanCollectionDataSource(despesasExtras);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Despesas_Extras_Por_Departamento.jasper"));
						
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

	private List<DespesaExtra> getDespesasExtra(
			List<DespesaPorVencimento> despesas) {
		ArrayList<DespesaExtra> extras = new ArrayList<DespesaExtra>(); 
		for(DespesaPorVencimento dpv : despesas){
			extras.addAll(dpv.getDespesaExtraCollection());
		}
		return extras;
	}
}