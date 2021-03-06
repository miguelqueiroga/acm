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

import ACM.model.entity.Despesa;
import ACM.model.entity.DespesaPorVencimento;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;
import ACM.util.DespesaImpressao;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportDespesasPorMes extends HttpServlet {

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
			Collection<DespesaPorVencimento> despesas = factoryService.getListPorConsulta(consulta);
			
			ArrayList<DespesaImpressao> despesasImpressao = organizaDespesasPorVencimento(despesas);
			
			// TODO Transformar os objetos DPV em objetos Despesas Impress�o.
			
			Collections.sort (despesasImpressao, new Comparator<DespesaImpressao>() {  
	            public int compare(DespesaImpressao o1, DespesaImpressao o2) {  
	                return o1.getDesNome().compareTo(o2.getDesNome());  
	            }  
	        });
			
			JRDataSource jrds = new JRBeanCollectionDataSource(despesasImpressao);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Despesas_Vencimento.jasper"));
						
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

	private ArrayList<DespesaImpressao> organizaDespesasPorVencimento(
			Collection<DespesaPorVencimento> despesas) {
		ArrayList<DespesaImpressao> despesasImpressao = new ArrayList<DespesaImpressao>();
		for(DespesaPorVencimento despesa : despesas){
			DespesaImpressao despesaImpressao = getDespesaImpresaoPorDespesa(despesasImpressao, despesa.getDpvDespesa()); 
			if(despesaImpressao == null){
				despesaImpressao = new DespesaImpressao(despesa.getDpvDespesa());
				despesasImpressao.add(despesaImpressao);
			}
			despesaImpressao.addDespesa(despesa);
		}
		return despesasImpressao;
	}

	private DespesaImpressao getDespesaImpresaoPorDespesa(
			Collection<DespesaImpressao> despesasImpressao,
			Despesa despesa) {
		for(DespesaImpressao des : despesasImpressao){
			if(des.getDespesa().getDesNome().equals(despesa.getDesNome()))
				return des;			
		}
		return null;
	}
}