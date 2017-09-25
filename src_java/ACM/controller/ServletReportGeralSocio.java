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

import ACM.model.entity.Aplicacao;
import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.Receita;
import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.Retirada;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;
import ACM.util.FinancasSocioImpressao;
import ACM.util.Tools;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportGeralSocio extends HttpServlet {

	protected ServletContext sc;
	
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
			List<FinancasSocioImpressao> financas = new ArrayList<FinancasSocioImpressao>(); 
			financas.addAll(getReceitasPorPeriodoSocio(socioId, dataInicial, dataFinal));
			financas.addAll(getReceitasAvulsasPorPeriodoSocio(socioId, dataInicial, dataFinal));
			financas.addAll(getDespesasPorPeriodoSocio(socioId, dataInicial, dataFinal));
			financas.addAll(getAplicacaosPorPeriodoSocio(socioId, dataInicial, dataFinal));
			
			/*Collections.sort (receitas, new Comparator<DespesaPorVencimento>() {  
	            public int compare(DespesaPorVencimento o1, DespesaPorVencimento o2) {  
	                return o1.getDpvDespesa().getDesNome().compareTo(o2.getDpvDespesa().getDesNome());  
	            }  
	        });	*/
			
			System.out.println("Tamanho final: " + financas.size());
			
			JRDataSource jrds = new JRBeanCollectionDataSource(financas);
			reportFile = new File(sc.getRealPath("WEB-INF/relatorios/Relatorio_Geral_Socio.jasper"));
						
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

	protected List<FinancasSocioImpressao> organizaDespesasPorVencimento(
			List<DespesaPorVencimento> despesas) {
		List<FinancasSocioImpressao> financas = new ArrayList<FinancasSocioImpressao>();
		HashMap<String, ArrayList<DespesaPorVencimento>> despesasPorVencimento = new HashMap<String, ArrayList<DespesaPorVencimento>>();
		for(DespesaPorVencimento dpv : despesas){ 
			String vencimento = dpv.getDpvVencimento().getVenNome();
			ArrayList<DespesaPorVencimento> despesasAtuais = new ArrayList<DespesaPorVencimento>();
			if(despesasPorVencimento.keySet().contains(vencimento))
				despesasPorVencimento.get(vencimento).add(dpv);
			else {
				despesasAtuais.add(dpv);
				despesasPorVencimento.put(vencimento, despesasAtuais);
			}			
		}
		for(String vencimento : despesasPorVencimento.keySet()){
			FinancasSocioImpressao fsi = new FinancasSocioImpressao();
			fsi.setVencimento(vencimento);
			fsi.setNome("(-) Despesas");
			double valor = 0.0;
			for(DespesaPorVencimento dpv : despesasPorVencimento.get(vencimento)){
				valor += Tools.stringToDouble(dpv.getValorRateado());
			}
			fsi.setValor(Tools.doubleToString(valor*-1));
			financas.add(fsi);
		}
		return financas;
	}
	
	protected List<FinancasSocioImpressao> organizaReceitasPorVencimento(
			List<Receita> receitas) {
		List<FinancasSocioImpressao> financas = new ArrayList<FinancasSocioImpressao>();
		HashMap<String, ArrayList<Receita>> receitasPorVencimento = new HashMap<String, ArrayList<Receita>>();
		for(Receita rec : receitas){ 
			String vencimento = rec.getRecReceitaPorConvenio().getRpcVencimento().getVenNome();
			ArrayList<Receita> receitasAtuais = new ArrayList<Receita>();
			if(receitasPorVencimento.keySet().contains(vencimento))
				receitasPorVencimento.get(vencimento).add(rec);
			else {
				receitasAtuais.add(rec);
				receitasPorVencimento.put(vencimento, receitasAtuais);
			}			
		}
		for(String vencimento : receitasPorVencimento.keySet()){
			FinancasSocioImpressao fsi = new FinancasSocioImpressao();
			fsi.setVencimento(vencimento);
			fsi.setNome("(+) Receitas");
			double valor = 0.0;
			for(Receita r : receitasPorVencimento.get(vencimento)){
				valor += Tools.stringToDouble(r.getValorRateado());
			}
			fsi.setValor(Tools.doubleToString(valor));
			financas.add(fsi);
		}
		return financas;
	}
	
	protected List<FinancasSocioImpressao> organizaReceitasAvulsasPorVencimento(List<ReceitaAvulsa> receitas) {
		List<FinancasSocioImpressao> financas = new ArrayList<FinancasSocioImpressao>();
		HashMap<String, ArrayList<ReceitaAvulsa>> receitasPorVencimento = new HashMap<String, ArrayList<ReceitaAvulsa>>();
		for(ReceitaAvulsa rec : receitas){ 
			String vencimento = rec.getRaVencimento().getVenNome();
			if(receitasPorVencimento.keySet().contains(vencimento))
				receitasPorVencimento.get(vencimento).add(rec);
			else {
				ArrayList<ReceitaAvulsa> receitasAtuais = new ArrayList<ReceitaAvulsa>();
				receitasAtuais.add(rec);
				receitasPorVencimento.put(vencimento, receitasAtuais);
			}			
		}
		for(String vencimento : receitasPorVencimento.keySet()){
			FinancasSocioImpressao fsi = new FinancasSocioImpressao();
			fsi.setVencimento(vencimento);
			fsi.setNome("(+) Receitas Avulsas");
			double valor = 0.0;
			for(ReceitaAvulsa r : receitasPorVencimento.get(vencimento)){
				valor += Tools.stringToDouble(r.getValorRateado());
			}
			fsi.setValor(Tools.doubleToString(valor));
			financas.add(fsi);
		}
		return financas;
	}
	
	protected List<FinancasSocioImpressao> organizaRetiradasPorVencimento(
			List<Retirada> retiradas) {
		List<FinancasSocioImpressao> financas = new ArrayList<FinancasSocioImpressao>();
		HashMap<String, ArrayList<Retirada>> retiradasPorVencimento = new HashMap<String, ArrayList<Retirada>>();
		for(Retirada ret : retiradas){ 
			String vencimento = ret.getVencimento().getVenNome();
			ArrayList<Retirada> retiradasAtuais = new ArrayList<Retirada>();
			if(retiradasPorVencimento.keySet().contains(vencimento))
				retiradasPorVencimento.get(vencimento).add(ret);
			else {
				retiradasAtuais.add(ret);
				retiradasPorVencimento.put(vencimento, retiradasAtuais);
			}			
		}
		for(String vencimento : retiradasPorVencimento.keySet()){
			FinancasSocioImpressao fsi = new FinancasSocioImpressao();
			fsi.setVencimento(vencimento);
			fsi.setNome("(-) Retiradas");
			double valor = 0.0;
			for(Retirada r : retiradasPorVencimento.get(vencimento)){
				valor += Tools.stringToDouble(r.getRetValor());
			}
			fsi.setValor(Tools.doubleToString(valor*-1));
			financas.add(fsi);
		}
		return financas;
	}
	
	protected List<FinancasSocioImpressao> organizaAplicacoesPorVencimento(
			List<Aplicacao> aplicacoes) {
		List<FinancasSocioImpressao> financas = new ArrayList<FinancasSocioImpressao>();
		HashMap<String, ArrayList<Aplicacao>> aplicacoesPorVencimento = new HashMap<String, ArrayList<Aplicacao>>();
		for(Aplicacao apl : aplicacoes){ 
			String vencimento = apl.getVencimento().getVenNome();
			ArrayList<Aplicacao> aplicacoesAtuais = new ArrayList<Aplicacao>();
			if(aplicacoesPorVencimento.keySet().contains(vencimento))
				aplicacoesPorVencimento.get(vencimento).add(apl);
			else {
				aplicacoesAtuais.add(apl);
				aplicacoesPorVencimento.put(vencimento, aplicacoesAtuais);
			}			
		}
		for(String vencimento : aplicacoesPorVencimento.keySet()){
			FinancasSocioImpressao fsi = new FinancasSocioImpressao();
			fsi.setVencimento(vencimento);
			fsi.setNome("(-) Aplicações");
			double valor = 0.0;
			for(Aplicacao a : aplicacoesPorVencimento.get(vencimento)){
				valor += Tools.stringToDouble(a.getAplValor());
			}
			fsi.setValor(Tools.doubleToString(valor*-1));
			financas.add(fsi);
		}
		return financas;
	}
	
	protected List<FinancasSocioImpressao> getDespesasPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		return organizaDespesasPorVencimento(ReportUtils.getDespesasPorPeriodoSocio(socioId, dataInicial, dataFinal, factoryService));
	}

	protected List<FinancasSocioImpressao> getReceitasPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		return organizaReceitasPorVencimento(ReportUtils.getReceitasPorPeriodoSocio(socioId, dataInicial, dataFinal, factoryService));
	}
	
	protected List<FinancasSocioImpressao> getReceitasAvulsasPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		return organizaReceitasAvulsasPorVencimento(ReportUtils.getReceitasAvulsasPorIntervaloESocia(socioId, dataInicial, dataFinal, factoryService));
	}

	protected List<FinancasSocioImpressao> getRetiradasPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		List<Retirada> retiradas = new ArrayList<Retirada>();
		String consulta = "";
		try {
			//System.out.println(consulta);
			consulta = "From ACM.model.entity.Retirada as r where r.socio.socId = " + socioId + " AND r.vencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			retiradas = factoryService.getListPorConsulta(consulta);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return organizaRetiradasPorVencimento(retiradas);
	}

	protected List<FinancasSocioImpressao> getAplicacaosPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		List<Aplicacao> aplicacoes = new ArrayList<Aplicacao>();
		String consulta = "";
		try {
			//System.out.println(consulta);
			consulta = "From ACM.model.entity.Aplicacao as r where r.socio.socId = " + socioId + " AND r.vencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			aplicacoes = factoryService.getListPorConsulta(consulta);			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return organizaAplicacoesPorVencimento(aplicacoes);
	}

}