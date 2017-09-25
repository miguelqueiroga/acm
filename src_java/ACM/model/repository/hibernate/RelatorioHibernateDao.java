package ACM.model.repository.hibernate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.ResumoAnual;
import ACM.util.Tools;

@Repository(value="RelatorioRepository")
public class RelatorioHibernateDao extends HibernateDao {
	private String consultaResumosAnuais = "getResultadoTodosAnos";

	@Autowired
	public RelatorioHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public List<ResumoAnual> getListResumosAnuaisPorSocio(String idSocio, String dataInicial, String dataFinal, List<ReceitaAvulsa> receitasAvulsas) {
		List<ResumoAnual> resumosAnuais = new ArrayList<ResumoAnual>(); 
		
		
		if(idSocio != null){
			resumosAnuais = getResumoPorSocia(idSocio);			
		}
		else {
			resumosAnuais.addAll(unificarResumos(getResumosTodasAsSocias()));
		}	
		
		Collections.sort(resumosAnuais, new Comparator<ResumoAnual>(){
			public int compare(ResumoAnual ra1, ResumoAnual ra2) {
				return ra1.getAno().compareTo(ra2.getAno());
			}			
		});
		int dataInicio = Integer.parseInt(dataInicial.substring(0, 4));
		int dataFim = Integer.parseInt(dataFinal.substring(0, 4));
		Iterator<ResumoAnual> resumos = resumosAnuais.iterator();
		while (resumos.hasNext()) {
			ResumoAnual resumoAnual = (ResumoAnual) resumos.next();
			if(Integer.parseInt(resumoAnual.getAno()) < dataInicio || Integer.parseInt(resumoAnual.getAno()) > dataFim){
				resumos.remove();
			}
		}
		return atualizaResumosComReceitasAvulsas(resumosAnuais, receitasAvulsas);
	}

	private List<ResumoAnual> atualizaResumosComReceitasAvulsas(List<ResumoAnual> resumosAnuais, List<ReceitaAvulsa> receitasAvulsas) {
		for (ResumoAnual resumoAnual : resumosAnuais) {
			double valorReceitasAvulsas = getSomatorioAvulsasPorAno(receitasAvulsas, resumoAnual.getAno());
			resumoAnual.setReceitasAvulsas(valorReceitasAvulsas+"");
		}
		return resumosAnuais;
	}

	private double getSomatorioAvulsasPorAno(List<ReceitaAvulsa> receitasAvulsas, String ano) {
		if(receitasAvulsas != null) {
			double valorReceitasAvulsasPorAno = 0.0; 
			for (ReceitaAvulsa receitaAvulsa : receitasAvulsas) {
				if(receitaAvulsa.getRaVencimento().getVenNome().startsWith(ano)) {
					valorReceitasAvulsasPorAno += Tools.stringToDouble(receitaAvulsa.getValorRateado());
				}
			}
			return valorReceitasAvulsasPorAno;
		}
		return 0.0;
	}

	private Collection<ResumoAnual> unificarResumos(
			List<ResumoAnual> resumosTodasAsSocias) {
		Set<String> anos = new HashSet<String>();
		for (ResumoAnual resumo : resumosTodasAsSocias) {
			anos.add(resumo.getAno());
		}
		HashMap<String, ResumoAnual> resumos = new HashMap<String, ResumoAnual>();
		for (String ano : anos) {
			ResumoAnual resumo = new ResumoAnual();
			resumo.setANO(ano);
			resumo.setAPLICACOES(null);
			resumo.setDESPESAS(null);
			resumo.setDINHEIRORECEBIDOEMCONTA(null);
			resumo.setRECEITAS(null);
			resumo.setRETIRADAS(null);
			resumos.put(ano, resumo);
		}
		for (ResumoAnual resumo : resumosTodasAsSocias) {
			ResumoAnual resumoAnual = resumos.get(resumo.getAno());
			Double aplicacoes = Tools.stringToDoubleMonetario(resumo.getAplicacoes()) + Tools.stringToDoubleMonetario(resumoAnual.getAplicacoes()); 
			resumoAnual.setAPLICACOES(BigDecimal.valueOf(aplicacoes));
			Double despesas = Tools.stringToDoubleMonetario(resumo.getDespesas()) + Tools.stringToDoubleMonetario(resumoAnual.getDespesas()); 
			resumoAnual.setDESPESAS(BigDecimal.valueOf(despesas));
			Double dinheiroRecebido = Tools.stringToDoubleMonetario(resumo.getDinheiroRecebidoEmConta()) + Tools.stringToDoubleMonetario(resumoAnual.getDinheiroRecebidoEmConta()); 
			resumoAnual.setDINHEIRORECEBIDOEMCONTA(BigDecimal.valueOf(dinheiroRecebido));
			Double receitas = Tools.stringToDoubleMonetario(resumo.getReceitas()) + Tools.stringToDoubleMonetario(resumoAnual.getReceitas()); 
			resumoAnual.setRECEITAS(BigDecimal.valueOf(receitas));
			Double retiradas = Tools.stringToDoubleMonetario(resumo.getRetiradas()) + Tools.stringToDoubleMonetario(resumoAnual.getRetiradas()); 
			resumoAnual.setRETIRADAS(BigDecimal.valueOf(retiradas));
		}		
		return resumos.values();
	}

	private List<ResumoAnual> getResumoPorSocia(String idSocio) {
		List<ResumoAnual> resumosAnuais;
		resumosAnuais = this.getValorPorProcedimento(consultaResumosAnuais, idSocio);
		if(idSocio.equals("1")){
			resumosAnuais.addAll(getResumosAnterioresGG());
		}
		else if(idSocio.equals("2")){
			resumosAnuais.addAll(getResumosAnterioresVV());
		}
		return resumosAnuais;
	}

	private List<ResumoAnual> getResumosTodasAsSocias() {
		List<ResumoAnual> retorno = new ArrayList<ResumoAnual>();
		List<ResumoAnual> consultaResumos = this.getValorPorProcedimento(consultaResumosAnuais, "1");
		retorno.addAll(consultaResumos);
		consultaResumos = this.getValorPorProcedimento(consultaResumosAnuais, "2");
		retorno.addAll(consultaResumos);
		retorno.addAll(getResumosAnterioresGG());
		retorno.addAll(getResumosAnterioresVV());
		return retorno;
	}
	
	private Collection<ResumoAnual> getResumosAnterioresVV() {
		List<ResumoAnual> resumosAnteriores = new ArrayList<ResumoAnual>();
		ResumoAnual ra = new ResumoAnual();
		ra.setANO("2008");
		ra.setRECEITAS(BigDecimal.valueOf(138575.70));
		ra.setDESPESAS(BigDecimal.valueOf(0.0));
		ra.setDINHEIRORECEBIDOEMCONTA(BigDecimal.valueOf(96734.05));
		ra.setRETIRADAS(BigDecimal.valueOf(45871.84));
		ra.setAPLICACOES(BigDecimal.valueOf(0.0));
		resumosAnteriores.add(ra);
		ra = new ResumoAnual();
		ra.setANO("2009");
		ra.setRECEITAS(BigDecimal.valueOf(292288.81));
		ra.setDESPESAS(BigDecimal.valueOf(0.0));
		ra.setDINHEIRORECEBIDOEMCONTA(BigDecimal.valueOf(164988.07));
		ra.setRETIRADAS(BigDecimal.valueOf(71000.00));
		ra.setAPLICACOES(BigDecimal.valueOf(0.0));
		resumosAnteriores.add(ra);
		ra = new ResumoAnual();
		ra.setANO("2010");
		ra.setRECEITAS(BigDecimal.valueOf(198964.72));
		ra.setDESPESAS(BigDecimal.valueOf(0.0));
		ra.setDINHEIRORECEBIDOEMCONTA(BigDecimal.valueOf(97164.74));
		ra.setRETIRADAS(BigDecimal.valueOf(121500.00));
		ra.setAPLICACOES(BigDecimal.valueOf(9000.0));
		resumosAnteriores.add(ra);
		return resumosAnteriores;
	}

	private Collection<ResumoAnual> getResumosAnterioresGG() {
		List<ResumoAnual> resumosAnteriores = new ArrayList<ResumoAnual>();
		ResumoAnual ra = new ResumoAnual();
		ra.setANO("2008");
		ra.setRECEITAS(BigDecimal.valueOf(79087.17));
		ra.setDESPESAS(BigDecimal.valueOf(0.0));
		ra.setDINHEIRORECEBIDOEMCONTA(BigDecimal.valueOf(0.0));
		ra.setRETIRADAS(BigDecimal.valueOf(16414.98));
		ra.setAPLICACOES(BigDecimal.valueOf(0.0));
		resumosAnteriores.add(ra);
		ra = new ResumoAnual();
		ra.setANO("2009");
		ra.setRECEITAS(BigDecimal.valueOf(356213.21));
		ra.setDESPESAS(BigDecimal.valueOf(0.0));
		ra.setDINHEIRORECEBIDOEMCONTA(BigDecimal.valueOf(179838.48));
		ra.setRETIRADAS(BigDecimal.valueOf(163000.00));
		ra.setAPLICACOES(BigDecimal.valueOf(0.0));
		resumosAnteriores.add(ra);
		ra = new ResumoAnual();
		ra.setANO("2010");
		ra.setRECEITAS(BigDecimal.valueOf(226477.48));
		ra.setDESPESAS(BigDecimal.valueOf(0.0));
		ra.setDINHEIRORECEBIDOEMCONTA(BigDecimal.valueOf(96507.81));
		ra.setRETIRADAS(BigDecimal.valueOf(100000.00));
		ra.setAPLICACOES(BigDecimal.valueOf(9000.0));
		resumosAnteriores.add(ra);
		return resumosAnteriores;
	}

	public Class<ResumoAnual> getClassName() {
		return ResumoAnual.class;
	}
}