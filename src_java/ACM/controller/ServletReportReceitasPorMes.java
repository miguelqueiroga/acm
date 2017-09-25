package ACM.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
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
import ACM.model.entity.ReceitaPorConvenio;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;
import ACM.util.ReceitaImpressao;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportReceitasPorMes extends HttpServlet {

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
		
		byte[] bytes = null;
		HashMap parameters = new HashMap();
		JasperPrint reportPrint = null;
		File reportFile = null;
		
		try{
			Collection<ReceitaPorConvenio> receitas = factoryService.getListPorConsulta(consulta);
			
			ArrayList<ReceitaImpressao> receitasImpressao = organizaReceitasPorConvenio(receitas);
			
			Collections.sort (receitasImpressao, new Comparator<ReceitaImpressao>() {  
	            public int compare(ReceitaImpressao o1, ReceitaImpressao o2) {  
	                return o1.getConNome().compareTo(o2.getConNome());  
	            }  
	        });
			
			JRDataSource jrds = new JRBeanCollectionDataSource(receitasImpressao);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Receitas_Vencimento.jasper"));
						
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

	private ArrayList<ReceitaImpressao> organizaReceitasPorConvenio(
			Collection<ReceitaPorConvenio> receitas) {
		ArrayList<ReceitaImpressao> receitasImpressao = new ArrayList<ReceitaImpressao>();
		for(ReceitaPorConvenio receita : receitas){
			ReceitaImpressao receitaImpressao = getReceitaImpresaoPorConvenio(receitasImpressao, receita.getRpcConvenio()); 
			if(receitaImpressao == null){
				receitaImpressao = new ReceitaImpressao(receita.getRpcConvenio());
				receitasImpressao.add(receitaImpressao);
			}
			receitaImpressao.addReceita(receita);
		}
		return receitasImpressao;
	}

	private ReceitaImpressao getReceitaImpresaoPorConvenio(
			Collection<ReceitaImpressao> receitasImpressao,
			Convenio convenio) {
		for(ReceitaImpressao ri : receitasImpressao){
			if(ri.getConNome().equals(convenio.getConNome()))
				return ri;			
		}
		return null;
	}
}