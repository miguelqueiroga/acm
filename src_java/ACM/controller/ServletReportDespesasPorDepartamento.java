package ACM.controller;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import ACM.util.InformacoesDepartamentosDonos;
import ACM.util.Tools;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportDespesasPorDepartamento extends HttpServlet {

	private ServletContext sc;
	
	private ConsultaService factoryService = new ConsultaServiceImpl();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		sc = config.getServletContext();
		
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(factoryService, "despesaPorVencimentoService");
	}

	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {	
		try {
			geraRelatorioUnico(request, response);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServletException(e1);
		}
	}
	
	@SuppressWarnings("unchecked")
	public void geraRelatorioUnico(HttpServletRequest request, HttpServletResponse response) throws Exception{
		File reportFile = null;
		
		HashMap parameters = new HashMap();

		String consulta = request.getParameter("consulta");
		String consultaIndiretos = request.getParameter("consultaIndiretos");
		String dataInicial = request.getParameter("dataInicial");
		String dataFinal = request.getParameter("dataFinal");
		String departamentoNome = request.getParameter("departamentoNome");
		Integer departamentoDonoId = new Integer(request.getParameter("departamento"));
		SimpleDateFormat dateOrigem = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat dateFormatada = new SimpleDateFormat("MMM/yyyy", new DateFormatSymbols(new java.util.Locale ("pt", "BR")));
		
		parameters.put("dataInicial", dateFormatada.format(dateOrigem.parse(dataInicial)));
		parameters.put("dataFinal", dateFormatada.format(dateOrigem.parse(dataFinal)));
		parameters.put("departamento", departamentoNome);
		
		System.out.println("Diretos: " + consulta);
		System.out.println("Indiretos: " + consultaIndiretos);
		
		List<DespesaPorVencimento> despesas = null;
		List<DespesaPorVencimento> despesasIndiretas = null;
		try {
			despesas = factoryService.getListPorConsulta(consulta);
			if(consultaIndiretos == null || consultaIndiretos.equals("") || consultaIndiretos.equals("null"))
				despesasIndiretas = new ArrayList<DespesaPorVencimento>();
			else despesasIndiretas = factoryService.getListPorConsulta(consultaIndiretos);			
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServletException(e1);
		}
		ajustaDespesas(despesas);
		calculaRateio(despesasIndiretas, departamentoDonoId);
		
		despesas.addAll(despesasIndiretas);
		
		reportFile = new File(request.getSession().getServletContext()
				.getRealPath("WEB-INF/relatorios/Relatorio_Despesas_Indiretas_Vencimento_CD.jasper"));
		
		JRDataSource jrds = new JRBeanCollectionDataSource(despesas);
		
		byte[] bytes = null;
		try {
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters,jrds);
			
			ReportUtils.exportByteArrayToPdfStream(bytes, response);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	private void calculaRateio(List<DespesaPorVencimento> despesasIndiretas, Integer departamentoDonoId) {
		double valorTotal = 0.0;
		for(DespesaPorVencimento dpv : despesasIndiretas) {
			String porcentagem = InformacoesDepartamentosDonos.getPorcentagemGeral(departamentoDonoId, dpv.getDpvDespesa().getDesDepartamento().getDepId(), dpv.getDpvVencimento());
			dpv.setPorcentagemDono(porcentagem);
			double valorRateado = Tools.stringToDouble(dpv.getDpvValor()) * (Tools.stringToDouble(porcentagem));
			dpv.setValorRateado(Tools.doubleToString(valorRateado/100));
			dpv.setDireta(false);
			valorTotal += Double.parseDouble(dpv.getValorRateado().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		System.out.println(valorTotal/100);
	}
	
	private void ajustaDespesas(List<DespesaPorVencimento> despesas) {
		for(DespesaPorVencimento dpv : despesas) {
			String porcentagem = "100,0";
			dpv.setPorcentagemDono(porcentagem);
			dpv.setValorRateado(dpv.getDpvValor());
			dpv.setDireta(true);
		}
	}
}