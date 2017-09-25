package ACM.controller;

import static ACM.util.InformacoesDepartamentosDonos.DATA_TRANSICAO;

import java.io.File;
import java.io.IOException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
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
import ACM.model.entity.Departamento;
import ACM.model.entity.DepartamentoSocio;
import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.Receita;
import ACM.model.entity.ReceitaAvulsa;
import ACM.model.service.ConsultaService;
import ACM.model.service.ConsultaServiceImpl;
import ACM.util.InformacoesDepartamentosDonos;
import ACM.util.ResumoMensal;
import ACM.util.Tools;
import edu.emory.mathcs.backport.java.util.Arrays;


/**
 * 
 * @author root
 * @version
 */

@SuppressWarnings("serial")
public class ServletReportResumoMensal extends HttpServlet {

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
		Boolean imprimirReceitas = Boolean.TRUE;
		if(request.getParameter("imprimirDetalhes") != null){ 
			imprimirReceitas = !request.getParameter("imprimirDetalhes").equals("NAO");
		}
		SimpleDateFormat dateOrigem = new SimpleDateFormat("yyyy/MM");
		SimpleDateFormat dateFormatada = new SimpleDateFormat("MMM/yyyy", new DateFormatSymbols(new java.util.Locale ("pt", "BR")));
		
		
		byte[] bytes = null;
		HashMap parameters = new HashMap();
		JasperPrint reportPrint = null;
		File reportFile = null;
		String nomeRelatorio = "WEB-INF/relatorios/Relatorio_Resumo_Mensal.jasper";
		
		
		try{
			parameters.put("dataInicial", dateFormatada.format(dateOrigem.parse(dataInicial)));
			parameters.put("dataFinal", dateFormatada.format(dateOrigem.parse(dataFinal)));
			parameters.put("imprimirReceitasDetalhadas", imprimirReceitas);
			List<ResumoMensal> resumos = new ArrayList<ResumoMensal>();
			List<ReceitaAvulsa> receitasAvulsas = new ArrayList<ReceitaAvulsa>();
			if (socioId != null && !socioId.equals("") && !socioId.equals("NaN")) {
				parameters.put("socio", socioNome);
				parameters.put("totalRecebido", getValorRecebidoPorPeriodoSocio(socioId, dataInicial, dataFinal));
				receitasAvulsas = getReceitasAvulsasPorSocia(socioId, dataInicial, dataFinal);
				resumos = getResumoMensalPorSocia(socioId, socioNome, dataInicial, dataFinal);
			}	
			else {
				nomeRelatorio = "WEB-INF/relatorios/Relatorio_Resumo_Mensal_Geral.jasper";	
				receitasAvulsas = getReceitasAvulsasGeral(dataInicial, dataFinal);
				resumos = getResumoMensalGeral(dataInicial, dataFinal);				
			}
			parameters.put("receitasAvulsas", receitasAvulsas);
			parameters.put("totalReceitasAvulsas", ReportUtils.getTotalReceitasAvulsas(receitasAvulsas));
			
			resumos = ordenarResumosPorDepartamentos(resumos);
			
				
			JRDataSource jrds = new JRBeanCollectionDataSource(resumos);
			reportFile = new File(sc.getRealPath(nomeRelatorio));
						
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

	protected List<ReceitaAvulsa> getReceitasAvulsasGeral(String dataInicial, String dataFinal) {
		return ReportUtils.getReceitasAvulsasPorIntervalo(dataInicial, dataFinal, factoryService);
	}

	protected List<ReceitaAvulsa> getReceitasAvulsasPorSocia(String socioId, String dataInicial,
			String dataFinal) {
		return ReportUtils.getReceitasAvulsasPorIntervaloESocia(socioId, dataInicial, dataFinal, factoryService);
	}

	protected List<ResumoMensal> ordenarResumosPorDepartamentos(
			List<ResumoMensal> resumos) {
		Collections.sort (resumos, new Comparator<ResumoMensal>() {  
            @SuppressWarnings("unchecked")
			public int compare(ResumoMensal o1, ResumoMensal o2) {  
            	String idsOrdenados[] = {"ULTRASSOM GG","ULTRASSOM VV","ULTRASSOM GERAL","MAMOGRAFIA GG","MAMOGRAFIA VV","MAMOGRAFIA GERAL","PROCEDIMENTOS GG","PROCEDIMENTOS VV","PROCEDIMENTOS","ULTRA-SOMMA"};
            	List<String> ordenacao = Arrays.asList(idsOrdenados);
                return Integer.valueOf(ordenacao.indexOf(o1.getDepartamento())).compareTo(Integer.valueOf(ordenacao.indexOf(o2.getDepartamento())));  
            }  
        });	
		return resumos;
	}

	protected List<ResumoMensal> getResumoMensalPorSocia(String socioId,
			String socioNome, String dataInicial, String dataFinal) {
		List<ResumoMensal> resumos = new ArrayList<ResumoMensal>(); 
		List<DepartamentoSocio> departamentosSocios = getDepartamentos(socioId);
		for(DepartamentoSocio ds : departamentosSocios){
			ResumoMensal resumo = new ResumoMensal(); 
			resumo.setNome(socioNome);
			resumo.setDataInicial(dataInicial);
			resumo.setDataFinal(dataFinal);
			resumo.setDepartamento(ds.getDsDepartamento().getDepNome());
			List<Receita> receitas = getReceitasPorPeriodoSocio(ds, dataInicial, dataFinal);
			Collection<DespesaPorVencimento> despesas = getDespesasPorPeriodoSocio(ds, dataInicial, dataFinal);
			resumo.setReceitas(receitas);
			resumo.setDespesas(despesas);
			resumo.setValorReceitas(receitas);
			resumo.setValorDespesas(despesas);
			if(!(resumo.getValorReceita().equals("0,00") && resumo.getValorDespesa().equals("0,00") && 
					resumo.getDepartamento().contains("MAMOGRAFIA"))){
				resumos.add(resumo);
			}
		}
		return resumos;
	}

	protected List<ResumoMensal> getResumoMensalGeral(String dataInicial, String dataFinal) {
		List<ResumoMensal> resumos = new ArrayList<ResumoMensal>(); 
		List<Departamento> departamentosSocios = getDepartamentos();
		for(Departamento ds : departamentosSocios){
			ResumoMensal resumo = new ResumoMensal(); 
			resumo.setDataInicial(dataInicial);
			resumo.setDataFinal(dataFinal);
			resumo.setDepartamento(ds.getDepNome());
			List<Receita> receitas = getReceitasPorPeriodoGeral(ds, dataInicial, dataFinal);
			Collection<DespesaPorVencimento> despesas = getDespesasPorPeriodoGeral(ds, dataInicial, dataFinal);
			resumo.setReceitas(receitas);
			resumo.setDespesas(despesas);
			resumo.setValorReceitas(receitas);
			resumo.setValorDespesas(despesas);
			if(!(resumo.getValorReceita().equals("0,00") && resumo.getValorDespesa().equals("0,00") && 
					resumo.getDepartamento().contains("MAMOGRAFIA"))){
				resumos.add(resumo);
			}
		}
		return resumos;
	}

	protected List<DepartamentoSocio> getDepartamentos(String socioId) {
		List<DepartamentoSocio> departamentosSocios = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DepartamentoSocio as d where d.dsSocio.socId = " + socioId; 
			departamentosSocios = factoryService.getListPorConsulta(consulta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departamentosSocios;
	}

	protected List<Departamento> getDepartamentos() {
		List<Departamento> departamentos = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Departamento as d";
			departamentos = factoryService.getListPorConsulta(consulta);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return departamentos;
	}
		
	protected List<DespesaPorVencimento> getDespesasPorPeriodoSocio(DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = new ArrayList<DespesaPorVencimento>();
		despesas.addAll(getDespesasDepartamentoPorIntervalo(ds, dataInicial, dataFinal));
		despesas.addAll(getDespesasIndiretasDepartamentoPorIntervalo(ds, dataInicial, dataFinal));		
		return despesas;
	}

	protected List<DespesaPorVencimento> getDespesasPorPeriodoGeral(Departamento ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = new ArrayList<DespesaPorVencimento>();
		despesas.addAll(getDespesasDepartamentoGeralPorIntervalo(ds, dataInicial, dataFinal));
		despesas.addAll(getDespesasIndiretasDepartamentoGeralPorIntervalo(ds, dataInicial, dataFinal));		
		return despesas;
	}

	protected List<DespesaPorVencimento> getDespesasDepartamentoPorIntervalo(
			DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DespesaPorVencimento as d where d.dpvDespesa.desDepartamento.depId = " + ds.getDsDepartamento().getDepId() + " AND d.dpvVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			//System.out.println(consulta);
			despesas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(despesas.size());
			for(DespesaPorVencimento d : despesas) {
				d.setPorcentagemDono(ds.getDsPorcentagem());
				d.setDireta(true);
				double valorLiquido = Tools.stringToDouble(d.getDpvValor());
				double porcentagem = Tools.stringToDouble(d.getPorcentagemDono()) / 100;
				d.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}

	protected List<DespesaPorVencimento> getDespesasDepartamentoGeralPorIntervalo(Departamento ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DespesaPorVencimento as d where d.dpvDespesa.desDepartamento.depId = " + ds.getDepId() + " AND d.dpvVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			//System.out.println(consulta);
			despesas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(despesas.size());
			for(DespesaPorVencimento d : despesas) {
				d.setPorcentagemDono("100,00");
				d.setDireta(true);
				double valorLiquido = Tools.stringToDouble(d.getDpvValor());
				double porcentagem = Tools.stringToDouble(d.getPorcentagemDono()) / 100;
				d.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}

	protected List<DespesaPorVencimento> getDespesasIndiretasDepartamentoPorIntervalo(
			DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DespesaPorVencimento as d where " + getClausulaPorListaDepartamentosIndiretos(ds.getDsDepartamento().getDepId(), "d.dpvDespesa.desDepartamento.depId", "d.dpvVencimento.venNome", dataInicial, dataFinal); 
			//System.out.println(consulta);
			despesas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(despesas.size());
			for(DespesaPorVencimento d : despesas) {
				double valorLiquido = Tools.stringToDouble(d.getDpvValor());
				double porcentagem = Tools.stringToDouble(InformacoesDepartamentosDonos.getPorcentagemGeral(ds.getDsDepartamento().getDepId(), d.getDpvDespesa().getDesDepartamento().getDepId(), d.getDpvVencimento())) / 100;
				porcentagem *= Tools.stringToDouble(ds.getDsPorcentagem()) / 100;
				d.setDireta(false);
				d.setPorcentagemDono(Tools.doubleToString(porcentagem * 100));
				d.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
			/*for (DepartamentoDepartamento ddIndireto : dd.getDdDepartamento().getDepartamentosDependentes()){
				despesas.addAll(getDespesasIndiretasDepartamentoPorIntervalo(ddIndireto, dataInicial, dataFinal, Tools.doubleToString((Tools.stringToDouble(porcentagemDS)*Tools.stringToDouble(dd.getDdPorcentagem())/100))));
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}

	protected List<DespesaPorVencimento> getDespesasIndiretasDepartamentoGeralPorIntervalo(Departamento ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DespesaPorVencimento as d where " + getClausulaPorListaDepartamentosIndiretos(ds.getDepId(), "d.dpvDespesa.desDepartamento.depId", "d.dpvVencimento.venNome", dataInicial, dataFinal); 
			//System.out.println(consulta);
			despesas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(despesas.size());
			for(DespesaPorVencimento d : despesas) {
				double valorLiquido = Tools.stringToDouble(d.getDpvValor());
				double porcentagem = Tools.stringToDouble(InformacoesDepartamentosDonos.getPorcentagemGeral(ds.getDepId(), d.getDpvDespesa().getDesDepartamento().getDepId(), d.getDpvVencimento())) / 100;
				d.setDireta(false);
				d.setPorcentagemDono(Tools.doubleToString(porcentagem * 100));
				d.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
			/*for (DepartamentoDepartamento ddIndireto : dd.getDdDepartamento().getDepartamentosDependentes()){
				despesas.addAll(getDespesasIndiretasDepartamentoPorIntervalo(ddIndireto, dataInicial, dataFinal, Tools.doubleToString((Tools.stringToDouble(porcentagemDS)*Tools.stringToDouble(dd.getDdPorcentagem())/100))));
			}*/
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}
	
	protected List<Receita> getReceitasPorPeriodoSocio(DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<Receita> receitas = new ArrayList<Receita>();
		receitas.addAll(getReceitasDepartamentoPorIntervalo(ds, dataInicial, dataFinal));
		receitas.addAll(getReceitasIndiretasDepartamentoPorIntervalo(ds, dataInicial, dataFinal));
		Collections.sort (receitas, new Comparator<Receita>() {  
            public int compare(Receita o1, Receita o2) {  
                return o1.getRecReceitaPorConvenio().getRpcConvenio().getConNome().compareTo(o2.getRecReceitaPorConvenio().getRpcConvenio().getConNome());  
            }  
        });				
		return receitas;
	}

	protected List<Receita> getReceitasPorPeriodoGeral(Departamento ds, String dataInicial, String dataFinal) {
		List<Receita> receitas = new ArrayList<Receita>();
		receitas.addAll(getReceitasDepartamentoGeralPorIntervalo(ds, dataInicial, dataFinal));
		receitas.addAll(getReceitasIndiretasDepartamentoGeralPorIntervalo(ds, dataInicial, dataFinal));
		Collections.sort (receitas, new Comparator<Receita>() {  
            public int compare(Receita o1, Receita o2) {  
                return o1.getRecReceitaPorConvenio().getRpcConvenio().getConNome().compareTo(o2.getRecReceitaPorConvenio().getRpcConvenio().getConNome());  
            }  
        });				
		return receitas;
	}

	protected List<Receita> getReceitasDepartamentoPorIntervalo(
			DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<Receita> receitas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Receita as r where r.recDepartamento.depId = " + ds.getDsDepartamento().getDepId() + " AND r.recReceitaPorConvenio.rpcVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			//System.out.println(consulta);
			receitas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(receitas.size());
			for(Receita r : receitas) {
				r.setPorcentagemDono(ds.getDsPorcentagem());
				r.setDireta(true);
				double valorLiquido = Tools.stringToDouble(r.getRecValorLiquido());
				double porcentagem = Tools.stringToDouble(r.getPorcentagemDono()) / 100;
				r.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}

	protected List<Receita> getReceitasDepartamentoGeralPorIntervalo(Departamento d, String dataInicial, String dataFinal) {
		List<Receita> receitas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Receita as r where r.recDepartamento.depId = " + d.getDepId() + " AND r.recReceitaPorConvenio.rpcVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			//System.out.println(consulta);
			receitas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(receitas.size());
			for(Receita r : receitas) {
				r.setPorcentagemDono("100,00");
				r.setDireta(true);
				double valorLiquido = Tools.stringToDouble(r.getRecValorLiquido());
				double porcentagem = Tools.stringToDouble(r.getPorcentagemDono()) / 100;
				r.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}

	protected String getClausulaPorListaDepartamentosIndiretos(Integer depId, String departamento, String vencimento, String dataInicial, String dataFinal) {
		String lista = "";
		
		if (isDataEmTransicao(dataInicial, dataFinal)){ 
			lista = getListaIdsParaConsulta(InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(depId).iterator());
			String retorno = "(" + departamento + " in (" + lista + ") AND " + vencimento + " BETWEEN '" + dataInicial + "' AND '2014/09')";
			lista = getListaIdsParaConsulta(InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(depId).iterator());
			retorno += " OR (" + departamento + " in (" + lista + ") AND " + vencimento + " BETWEEN '2014/10' AND '" + dataFinal + "')";
			return retorno;
		}
		else {
			if (dataFinal.compareTo(DATA_TRANSICAO) < 0){
				lista = getListaIdsParaConsulta(InformacoesDepartamentosDonos.getDepartamentosIndiretosEstruturaAntiga(depId).iterator());
			}
			else {
				lista = getListaIdsParaConsulta(InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(depId).iterator());
			}			
			
			String retorno = departamento + " in (" + lista + ") AND " + vencimento + " BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'";
			return retorno;
		}		
	}

	protected String getListaIdsParaConsulta(Iterator<String> idsIndiretos) {
		String lista = "";
		while(idsIndiretos.hasNext()) {
			String id = idsIndiretos.next();
			lista += id;
			if(idsIndiretos.hasNext()){
				lista += ", ";
			}
		}
		return lista;
	}

	protected boolean isDataEmTransicao(String dataInicial, String dataFinal) {
		return dataFinal != null && dataInicial != null && (dataInicial.compareTo(DATA_TRANSICAO) < 0) && (dataFinal.compareTo(DATA_TRANSICAO) >= 0);
	}

	protected List<Receita> getReceitasIndiretasDepartamentoPorIntervalo(
			DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<Receita> receitas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Receita as r where " + getClausulaPorListaDepartamentosIndiretos(ds.getDsDepartamento().getDepId(), "r.recDepartamento.depId", "r.recReceitaPorConvenio.rpcVencimento.venNome", dataInicial, dataFinal); 
			//System.out.println(consulta);
			receitas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(despesas.size());
			for(Receita r : receitas) {
				double valorLiquido = Tools.stringToDouble(r.getRecValorLiquido());
				double porcentagem = Tools.stringToDouble(InformacoesDepartamentosDonos.getPorcentagemGeral(ds.getDsDepartamento().getDepId(), r.getRecDepartamento().getDepId(), r.getRecReceitaPorConvenio().getRpcVencimento())) / 100;
				r.setDireta(false);
				porcentagem *= Tools.stringToDouble(ds.getDsPorcentagem()) / 100;
				r.setPorcentagemDono(Tools.doubleToString(porcentagem * 100));
				r.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}
	
	protected List<Receita> getReceitasIndiretasDepartamentoGeralPorIntervalo(Departamento ds, String dataInicial, String dataFinal) {
		List<Receita> receitas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Receita as r where " + getClausulaPorListaDepartamentosIndiretos(ds.getDepId(), "r.recDepartamento.depId", "r.recReceitaPorConvenio.rpcVencimento.venNome", dataInicial, dataFinal); 
			//System.out.println(consulta);
			receitas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(despesas.size());
			for(Receita r : receitas) {
				double valorLiquido = Tools.stringToDouble(r.getRecValorLiquido());
				double porcentagem = Tools.stringToDouble(InformacoesDepartamentosDonos.getPorcentagemGeral(ds.getDepId(), r.getRecDepartamento().getDepId(), r.getRecReceitaPorConvenio().getRpcVencimento())) / 100;
				r.setDireta(false);
				r.setPorcentagemDono(Tools.doubleToString(porcentagem * 100));
				r.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}
	
	protected double getValorRecebidoPorPeriodoSocio(String socioId, String dataInicial, String dataFinal) {
		double total = 0.0;
		List<Receita> receitas = new ArrayList<Receita>();
		List<ContaPagamento> contasSocios = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.ContaPagamento as conta where conta.cpSocio.socId = " + socioId; 
			System.out.println(factoryService.getListPorConsulta(consulta));
			contasSocios = factoryService.getListPorConsulta(consulta);
			for(ContaPagamento ct : contasSocios){
				receitas.addAll(getValorRecebidoContaPorIntervalo(ct, dataInicial, dataFinal));
			}
			for(Receita receita : receitas) {
				total += Tools.stringToDouble(receita.getValorRateado());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return total;
	}
	
	protected List<Receita> getValorRecebidoContaPorIntervalo(
			ContaPagamento cp, String dataInicial, String dataFinal) {
		List<Receita> receitas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Receita as r where r.recReceitaPorConvenio.rpcConvenio.conContaPagamento.cpId = " + cp.getCpId() + " AND r.recReceitaPorConvenio.rpcVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			System.out.println(consulta);
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