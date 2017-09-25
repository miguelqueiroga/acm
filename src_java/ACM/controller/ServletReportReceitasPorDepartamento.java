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

import ACM.model.entity.Receita;
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
public class ServletReportReceitasPorDepartamento extends HttpServlet {

	private ServletContext sc;
	
	private ConsultaService factoryService = new ConsultaServiceImpl();

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		sc = config.getServletContext();
		
		WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sc);

		AutowireCapableBeanFactory autowireCapableBeanFactory = webApplicationContext.getAutowireCapableBeanFactory();

		autowireCapableBeanFactory.configureBean(factoryService, "receitaPorConvenioService");
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
		
		List<Receita> receitas = null;
		List<Receita> receitasIndiretas = null;
		try {
			receitas = factoryService.getListPorConsulta(consulta);
			if(consultaIndiretos == null || consultaIndiretos.equals("") || consultaIndiretos.equals("null"))
				receitasIndiretas = new ArrayList<Receita>();
			else receitasIndiretas = factoryService.getListPorConsulta(consultaIndiretos);			
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new ServletException(e1);
		}
		ajustaReceitas(receitas);
		calculaRateio(receitasIndiretas, departamentoDonoId);
		
		receitas.addAll(receitasIndiretas);
		
		reportFile = new File(request.getSession().getServletContext()
				.getRealPath("WEB-INF/relatorios/Relatorio_Receitas_Indiretas_Vencimento_CD.jasper"));
		
		JRDataSource jrds = new JRBeanCollectionDataSource(receitas);
		
		byte[] bytes = null;
		try {
			bytes = JasperRunManager.runReportToPdf(reportFile.getPath(), parameters,jrds);
			
			ReportUtils.exportByteArrayToPdfStream(bytes, response);
		} catch (JRException e) {
			e.printStackTrace();
		}
	}

	private void calculaRateio(List<Receita> receitasIndiretas, Integer departamentoDonoId) {
		double valorTotal = 0.0;
		for(Receita receita : receitasIndiretas) {
			String porcentagem = InformacoesDepartamentosDonos.getPorcentagemGeral(departamentoDonoId, receita.getRecDepartamento().getDepId(), receita.getRecReceitaPorConvenio().getRpcVencimento());
			receita.setPorcentagemDono(porcentagem);
			double valorRateado = Tools.stringToDouble(receita.getRecValorLiquido()) * (Tools.stringToDouble(porcentagem));
			receita.setValorRateado(Tools.doubleToString(valorRateado/100));
			receita.setDireta(false);
			valorTotal += Double.parseDouble(receita.getValorRateado().replaceAll("\\.", "").replaceAll(",", "\\."));
		}
		System.out.println(valorTotal/100);
	}
	
	private void ajustaReceitas(List<Receita> receitas) {
		for(Receita receita : receitas) {
			String porcentagem = "100,0";
			receita.setPorcentagemDono(porcentagem);
			receita.setValorRateado(receita.getRecValorLiquido());
			receita.setDireta(true);
		}
	}
}