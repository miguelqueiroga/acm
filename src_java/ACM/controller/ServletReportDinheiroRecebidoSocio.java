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

import ACM.model.entity.ContaPagamento;
import ACM.model.entity.Receita;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;
import ACM.util.Tools;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportDinheiroRecebidoSocio extends HttpServlet {

	private ServletContext sc;
	
	protected ConsultaService factoryService = new ConsultaServiceImpl();

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
		if(socioId == null || socioId.equals(""))
			socioId = "NULL";
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
			
			List<Receita> receitas = getValorRecebidoPorPeriodoSocio(socioId, dataInicial, dataFinal);
			Collections.sort (receitas, new Comparator<Receita>() {  
	            public int compare(Receita o1, Receita o2) {  
	            	if (o1.getRecReceitaPorConvenio().getRpcVencimento().getVenNome().compareTo(o2.getRecReceitaPorConvenio().getRpcVencimento().getVenNome()) == 0)
	            		return o1.getRecReceitaPorConvenio().getRpcConvenio().getConNome().compareTo(o2.getRecReceitaPorConvenio().getRpcConvenio().getConNome());
	            	else return o1.getRecReceitaPorConvenio().getRpcVencimento().getVenNome().compareTo(o2.getRecReceitaPorConvenio().getRpcVencimento().getVenNome());
	            }  
	        });	
			
			System.out.println("Tamanho final: " + receitas.size());
			
			JRDataSource jrds = new JRBeanCollectionDataSource(receitas);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Dinheiro_Recebido_Socio.jasper"));
						
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

	protected List<Receita> getValorRecebidoPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		List<Receita> receitas = new ArrayList<Receita>();
		List<ContaPagamento> contasSocios = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.ContaPagamento as conta where conta.cpSocio.socId = " + socioId; 
			contasSocios = factoryService.getListPorConsulta(consulta);
			for(ContaPagamento ct : contasSocios){
				receitas.addAll(getValorRecebidoContaPorIntervalo(ct, dataInicial, dataFinal));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}

	private List<Receita> getValorRecebidoContaPorIntervalo(
			ContaPagamento cp, String dataInicial, String dataFinal) {
		List<Receita> receitas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Receita as r where r.recReceitaPorConvenio.rpcConvenio.conContaPagamento.cpId = " + cp.getCpId() + " AND r.recReceitaPorConvenio.rpcVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			receitas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(receitas.size());
			for(Receita r : receitas) {
				r.setPorcentagemDono("100");
				double valorLiquido = Tools.stringToDouble(r.getRecValorLiquido());
				double porcentagem = Tools.stringToDouble(r.getPorcentagemDono()) / 100;
				r.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}
}