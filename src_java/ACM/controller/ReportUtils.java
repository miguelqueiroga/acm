package ACM.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import ACM.model.entity.Aplicacao;
import ACM.model.entity.DepartamentoSocio;
import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.Receita;
import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.ReceitaAvulsaSocio;
import ACM.model.entity.Retirada;
import ACM.model.entity.Socio;
import ACM.model.entity.Vencimento;
import ACM.model.service.ConsultaService;
import ACM.util.InformacoesDepartamentosDonos;
import ACM.util.Tools;

public class ReportUtils {
	
	private static final double QUANTIDADE_SOCIOS = 2;
	private static ConsultaService factoryService;	
	
	public static void exportMultipleReportsToPdfStream(List<JasperPrint> prints, OutputStream output) throws JRException { 
		if(prints.isEmpty())
			return;
		JRPdfExporter exporter = new JRPdfExporter(); 
		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST, prints); 
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, output); 
		exporter.exportReport(); 
	}
	
	public static void exportByteArrayToPdfStream(byte[] bytes, HttpServletResponse response) throws JRException, IOException { 
		if (bytes != null && bytes.length > 0) {
			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);
			ServletOutputStream ouputStream = response.getOutputStream();
			ouputStream.write(bytes, 0, bytes.length);
			ouputStream.flush();
			ouputStream.close();
		}
	}

	public static Double getTotalReceitasAvulsas(List<ReceitaAvulsa> receitasAvulsas) {
		double total = 0.0;
		if(receitasAvulsas != null) {
			for (ReceitaAvulsa receitaAvulsa : receitasAvulsas) {
				total += Tools.stringToDouble(receitaAvulsa.getValorRateado());
			}
		}
		return Double.valueOf(total);
	}
	
	public static List<ReceitaAvulsa> getReceitasAvulsasPorIntervalo(String dataInicial, String dataFinal, ConsultaService factoryService) {
		List<ReceitaAvulsa> receitas = getReceitasAvulsas(dataInicial, dataFinal, factoryService);		
		for(ReceitaAvulsa r : receitas) {
			r.setValorRateado(r.getRaValor());
		}
		return receitas;
	}

	public static List<ReceitaAvulsa> getReceitasAvulsasPorIntervaloESocia(String idSocio, String dataInicial, String dataFinal, ConsultaService factoryService) {
		List<ReceitaAvulsa> receitas = getReceitasAvulsas(dataInicial, dataFinal, factoryService);	
		List<ReceitaAvulsa> retorno = new ArrayList<ReceitaAvulsa>();	
		for(ReceitaAvulsa r : receitas) {
			double valorLiquido = Tools.stringToDouble(r.getRaValor());
			double porcentagem = getPercentualPorSocios(idSocio, r) ;
			if(porcentagem > 0.0) {
				r.setValorRateado(Tools.doubleToString(valorLiquido * (porcentagem / 100)));
				retorno.add(r);
			}
		}
		return retorno;
	}

	private static List<ReceitaAvulsa> getReceitasAvulsas(String dataInicial, String dataFinal, ConsultaService factoryService){
		List<ReceitaAvulsa> receitas = null;
		String consulta;
		try {
			consulta = "From ACM.model.entity.ReceitaAvulsa as r where r.raVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			receitas = factoryService.getListPorConsulta(consulta);
		} catch (Exception e) {
			e.printStackTrace();
		}		
		return receitas;
	}

	private static double getPercentualPorSocios(String idSocio, ReceitaAvulsa r) {
		if (r != null && r.getRaReceitasAvulsasSocio() != null) {
			for (ReceitaAvulsaSocio receitaAvulsaSocio : r.getRaReceitasAvulsasSocio()) {
				if(idSocio.equals(receitaAvulsaSocio.getRasSocio().getSocId().toString())) {
					return Tools.stringToDouble(receitaAvulsaSocio.getRasPorcentagem());
				}
			}
			if(r.getRaReceitasAvulsasSocio().size() > 0) {
				return 0.0;
			}
		}
		return 100.0/QUANTIDADE_SOCIOS;
	}
	
	public static Double getQuantidadeExamesConvenioComValorPorPeriodoSocio(String socioId, String dataInicial, String dataFinal, List<Receita> receitasPorPeriodoSocio) {
		double result = 0;
		for (Receita receita : receitasPorPeriodoSocio) {
			if(receita.getRecQuantidadeExames() != null && !receita.getRecQuantidadeExames().equals("") &&
					(receita.getRecReceitaPorConvenio().getRpcConvenio().getConPagoAVista().equalsIgnoreCase("SIM")))
				result += Integer.valueOf(receita.getRecQuantidadeExames()).intValue() * Tools.stringToDouble(receita.getPorcentagemDono()) / 100;
		}
		return result;
	}
	
	public static Double getQuantidadeExamesConvenioSemValorPorPeriodoSocio(String socioId, String dataInicial, String dataFinal, List<Receita> receitasPorPeriodoSocio) {
		double result = 0;
		for (Receita receita : receitasPorPeriodoSocio) {
			if(receita.getRecQuantidadeExames() != null && !receita.getRecQuantidadeExames().equals("") &&
					!(receita.getRecReceitaPorConvenio().getRpcConvenio().getConPagoAVista().equalsIgnoreCase("SIM")))
				result += Integer.valueOf(receita.getRecQuantidadeExames()).intValue() * Tools.stringToDouble(receita.getPorcentagemDono()) / 100;
		}
		return result;
	}
		
	public static Integer getQuantidadeExamesComValorPorPeriodoGeral(String dataInicial, String dataFinal, List<Receita> receitas) {
		int result = 0;
		try {
			for(Receita r : receitas) {
				if(r.getRecQuantidadeExames() != null && !r.getRecQuantidadeExames().equals("") &&
						(r.getRecReceitaPorConvenio().getRpcConvenio().getConPagoAVista().equalsIgnoreCase("SIM"))
						&& r.getDireta())
					result += Integer.valueOf(r.getRecQuantidadeExames());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
		
	public static Integer getQuantidadeExamesSemValorPorPeriodoGeral(String dataInicial, String dataFinal, List<Receita> receitas) {
		int result = 0;
		try {
			for(Receita r : receitas) {
				if(r.getRecQuantidadeExames() != null && !r.getRecQuantidadeExames().equals("") &&
						!(r.getRecReceitaPorConvenio().getRpcConvenio().getConPagoAVista().equalsIgnoreCase("SIM"))
						&& r.getDireta())
					result += Integer.valueOf(r.getRecQuantidadeExames());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<Receita> getReceitasParaQuantidadeExamesPorPeriodoSocio(String socioId, String dataInicial, String dataFinal, ConsultaService factoryService) {
		ReportUtils.factoryService = factoryService;
		List<Receita> receitas = new ArrayList<Receita>();
		List<DepartamentoSocio> departamentosSocios = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DepartamentoSocio as d where d.dsSocio.socId = " + socioId; 
			departamentosSocios = factoryService.getListPorConsulta(consulta);
			for(DepartamentoSocio ds : departamentosSocios){
				receitas.addAll(getReceitasDepartamentoPorIntervalo(ds, dataInicial, dataFinal));
				receitas.addAll(getReceitasIndiretasPorIntervalo(ds, dataInicial, dataFinal));
			}
			for (Receita receita : receitas) {
				receita.setSocia(getSocioPorId(departamentosSocios,socioId));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}
	
	
	
	private static Socio getSocioPorId(
			List<DepartamentoSocio> departamentosSocios, String socioId) {
		for (DepartamentoSocio departamentoSocio : departamentosSocios) {
			if(socioId.equals(departamentoSocio.getDsSocio().getSocId().toString())){
				return departamentoSocio.getDsSocio();
			}
		}
		return null;
	}

	public static List<Receita> getReceitasPorPeriodoSocio(String socioId, String dataInicial, String dataFinal, ConsultaService factoryService) {
		ReportUtils.factoryService = factoryService;
		List<Receita> receitas = new ArrayList<Receita>();
		List<DepartamentoSocio> departamentosSocios = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DepartamentoSocio as d where d.dsSocio.socId = " + socioId; 
			//System.out.println(consulta);
			departamentosSocios = factoryService.getListPorConsulta(consulta);
			//System.out.println(departamentosSocios.size());
			for(DepartamentoSocio ds : departamentosSocios){
				receitas.addAll(getReceitasDepartamentoPorIntervalo(ds, dataInicial, dataFinal));
				receitas.addAll(getReceitasIndiretasPorIntervalo(ds, dataInicial, dataFinal));				
			}
			for (Receita receita : receitas) {
				receita.setSocia(departamentosSocios.get(0).getDsSocio());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}

	private static List<Receita> getReceitasDepartamentoPorIntervalo(
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
				double valorLiquido = Tools.stringToDouble(r.getRecValorLiquido());
				double porcentagem = Tools.stringToDouble(r.getPorcentagemDono()) / 100;
				r.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}
	
	private static List<Receita> getReceitasIndiretasPorIntervalo(
			DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<Receita> receitas = new ArrayList<Receita>();
		try {
			for(String dd : InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(ds.getDsDepartamento().getDepId())){
				receitas.addAll(getReceitasIndiretasDepartamentoPorIntervalo(Integer.valueOf(dd), dataInicial, dataFinal, ds.getDsPorcentagem(), InformacoesDepartamentosDonos.getPorcentagemGeral(ds.getDsDepartamento().getDepId(), Integer.valueOf(dd), new Vencimento(null, dataFinal))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}

	private static List<Receita> getReceitasIndiretasDepartamentoPorIntervalo(
			Integer dd, String dataInicial, String dataFinal, String porcentagemDS, String porcentagemDD) {
		List<Receita> receitas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.Receita as r where r.recDepartamento.depId = " + dd + " AND r.recReceitaPorConvenio.rpcVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			//System.out.println(consulta);
			receitas = factoryService.getListPorConsulta(consulta);		
			//System.out.println(despesas.size());
			for(Receita r : receitas) {
				double valorLiquido = Tools.stringToDouble(r.getRecValorLiquido());
				double porcentagem = Tools.stringToDouble(porcentagemDD) / 100;
				porcentagem *= Tools.stringToDouble(porcentagemDS) / 100;
				r.setPorcentagemDono(Tools.doubleToString(porcentagem * 100));
				r.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return receitas;
	}
	
	public static List<DespesaPorVencimento> getDespesasPorPeriodoSocio(String socioId, String dataInicial, String dataFinal, ConsultaService factoryService) {
		ReportUtils.factoryService = factoryService;
		List<DespesaPorVencimento> despesas = new ArrayList<DespesaPorVencimento>();
		List<DepartamentoSocio> departamentosSocios = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DepartamentoSocio as d where d.dsSocio.socId = " + socioId; 
			departamentosSocios = factoryService.getListPorConsulta(consulta);
			for(DepartamentoSocio ds : departamentosSocios){
				despesas.addAll(getDespesasDepartamentoPorIntervalo(ds, dataInicial, dataFinal));
				despesas.addAll(getDespesasIndiretasPorIntervalo(ds, dataInicial, dataFinal));
				//TODO 
				//if(ds.getDsDepartamento().isEspecial())
					//despesas.addAll(getDespesasIndiretasFilhosPorIntervalo(ds, dataInicial, dataFinal));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}
	
	private static List<DespesaPorVencimento> getDespesasDepartamentoPorIntervalo(
			DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DespesaPorVencimento as d where d.dpvDespesa.desDepartamento.depId = " + ds.getDsDepartamento().getDepId() + " AND d.dpvVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			despesas = factoryService.getListPorConsulta(consulta);		
			for(DespesaPorVencimento d : despesas) {
				d.setPorcentagemDono(ds.getDsPorcentagem());
				double valorLiquido = Tools.stringToDouble(d.getDpvValor());
				double porcentagem = Tools.stringToDouble(d.getPorcentagemDono()) / 100;
				d.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}
	
	private static List<DespesaPorVencimento> getDespesasIndiretasPorIntervalo(
			DepartamentoSocio ds, String dataInicial, String dataFinal) {
		List<DespesaPorVencimento> despesas = new ArrayList<DespesaPorVencimento>();
		try {
			for(String dd : InformacoesDepartamentosDonos.getDepartamentosIndiretosNovaEstrutura(ds.getDsDepartamento().getDepId())){
				despesas.addAll(getDespesasIndiretasDepartamentoPorIntervalo(Integer.valueOf(dd), dataInicial, dataFinal, ds.getDsPorcentagem(), InformacoesDepartamentosDonos.getPorcentagemGeral(ds.getDsDepartamento().getDepId(), Integer.valueOf(dd), new Vencimento(null, dataFinal))));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}

	private static List<DespesaPorVencimento> getDespesasIndiretasDepartamentoPorIntervalo(
			Integer dd, String dataInicial, String dataFinal, String porcentagemDS, String porcentagemDD) {
		List<DespesaPorVencimento> despesas = null;
		String consulta = "";
		try {
			consulta = "From ACM.model.entity.DespesaPorVencimento as d where d.dpvDespesa.desDepartamento.depId = " + dd + " AND d.dpvVencimento.venNome BETWEEN '" + dataInicial + "' AND '" + dataFinal + "'"; 
			despesas = factoryService.getListPorConsulta(consulta);		
			for(DespesaPorVencimento d : despesas) {
				double valorLiquido = Tools.stringToDouble(d.getDpvValor());
				double porcentagem = Tools.stringToDouble(porcentagemDD) / 100;
				porcentagem *= Tools.stringToDouble(porcentagemDS) / 100;
				d.setPorcentagemDono(Tools.doubleToString(porcentagem * 100));
				d.setValorRateado(Tools.doubleToString(valorLiquido * porcentagem));
			}			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return despesas;
	}

	public static Object getTotalRetiradas(List<Retirada> retiradas) {

		double total = 0.0;
		if(retiradas != null) {
			for (Retirada retirada : retiradas) {
				total += Tools.stringToDouble(retirada.getRetValor());
			}
		}
		return Double.valueOf(total);
	}

	public static Object getTotalAplicacoes(List<Aplicacao> aplicacoes) {

		double total = 0.0;
		if(aplicacoes != null) {
			for (Aplicacao aplicacao : aplicacoes) {
				total += Tools.stringToDouble(aplicacao.getAplValor());
			}
		}
		return Double.valueOf(total);
	}

}
