package ACM.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import ACM.model.entity.DespesaPorVencimento;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportDespesasPorMesCT extends HttpServlet {

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

	protected void service(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {	
		
		try {
			geraRelatorioUnico(request, response);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServletException(e1);
		}

		
	}
	
	@SuppressWarnings("unchecked")
	public void geraRelatorioUnico(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
		File reportFile = null;
		
		HashMap parameters = new HashMap();

		String consulta = request.getParameter("consulta");
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		String departamentos = request.getParameter("departamentos");
		String categorias = request.getParameter("categorias");
		SimpleDateFormat dateOrigem = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat dateFormatada = new SimpleDateFormat("MMM/yyyy", new DateFormatSymbols(new java.util.Locale ("pt", "BR")));
		
		
		
		List<DespesaPorVencimento> dados = null;
		try {
			parameters.put("dataInicial", dateFormatada.format(dateOrigem.parse(dataInicial)));
			parameters.put("dataFinal", dateFormatada.format(dateOrigem.parse(dataFinal)));
			parameters.put("departamentos", departamentos);
			parameters.put("categorias", categorias);
			
			dados = factoryService.getListPorConsulta(consulta);
			Collections.sort (dados, new Comparator<DespesaPorVencimento>() {  
	            public int compare(DespesaPorVencimento o1, DespesaPorVencimento o2) {  
	            	if (o1.getDpvVencimento().getVenNome().compareTo(o2.getDpvVencimento().getVenNome()) == 0)
	            		return o1.getDpvDespesa().getDesNome().compareTo(o2.getDpvDespesa().getDesNome());
	            	else return o1.getDpvVencimento().getVenNome().compareTo(o2.getDpvVencimento().getVenNome());  
	            }  
	        });
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServletException(e1);
		}

		reportFile = new File(request.getSession().getServletContext()
				.getRealPath("WEB-INF/relatorios/Relatorio_Despesas_Vencimento_CD.jasper"));
		
		JRDataSource jrds = new JRBeanCollectionDataSource(dados);
		
		byte[] bytes = null;
		try {
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(),
					parameters,jrds);
			
			ReportUtils.exportByteArrayToPdfStream(bytes, response);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

}