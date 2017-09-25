package ACM.model.repository.hibernate;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.ReceitaPorConvenio;
import ACM.model.entity.Vencimento;
import ACM.util.Pendencia;
import ACM.util.Tools;

@Repository(value="SistemaRepository")
public class SistemaHibernateDao extends HibernateDao {
	@Autowired
	public SistemaHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}
	
	public boolean finalizaMes(Boolean removeZeradas) {
		try {
			VencimentoHibernateDao vr = new VencimentoHibernateDao(super.getSessionFactory());
			DespesaPorVencimentoHibernateDao dpv = new DespesaPorVencimentoHibernateDao(super.getSessionFactory());
			
			Vencimento vencAtual = vr.getVencimentoAtual();
			Vencimento proximo = new Vencimento();
			StringTokenizer st = new StringTokenizer(vencAtual.getVenNome(), "/");
			int ano = Integer.parseInt(st.nextToken());
			int mes = Integer.parseInt(st.nextToken());
			if(mes == 12){
				mes = 1;
				ano++;
			}
			else mes++;
			proximo.setVenNome(ano + "/" + (mes < 10 ? "0" : "") + mes);
			vr.save(proximo);
			
			List<DespesaPorVencimento> despesas = dpv.getListPorVencimento(vencAtual);
			for (DespesaPorVencimento d : despesas){
				if(removeZeradas && d.getDpvValor().equals("0,00")){
					dpv.remove(d);
				}
				else {
					DespesaPorVencimento nova = new DespesaPorVencimento();
					nova.setDpvValor("0,00");
					nova.setDpvVencimento(proximo);
					nova.setDpvDespesa(d.getDpvDespesa());
					dpv.save(nova);
				}
			}
			return true;
		}
		catch (Exception e) {
			e.printStackTrace();
			return false;
		}		
	}

	public List<Pendencia> getPendenciasFechamento() {
		try {
			List<Pendencia> pendencias = new ArrayList<Pendencia>();
			Vencimento vencAtual = new VencimentoHibernateDao(super.getSessionFactory()).getVencimentoAtual();
			List<DespesaPorVencimento> despesas = new DespesaPorVencimentoHibernateDao(super.getSessionFactory()).getListPorVencimento(vencAtual);
			List<ReceitaPorConvenio> receitas = new ReceitaPorConvenioHibernateDao(super.getSessionFactory()).getListPorVencimento(vencAtual);
			for (DespesaPorVencimento d : despesas){
				if(d.getDpvValor().equals("0,00"))
					pendencias.add(new Pendencia("A Despesa " + d.getDpvDespesa().getDesNome() + " da categoria " + d.getDpvDespesa().getDesCategoria().getCdNome() + " está com valor ZERO."));
			}
			for (ReceitaPorConvenio rc : receitas){
				double restante = rc.getValorBrutoRestante();
				if(restante > 0.0)
					pendencias.add(new Pendencia("Receita para o convênio " + rc.getRpcConvenio().getConNome() + " ainda possui R$ " + Tools.doubleToString(restante) + " em aberto."));
				int quantidadeRestante = rc.getQuantidadeExamesRestante();
				if(quantidadeRestante > 0.0)
					pendencias.add(new Pendencia("Receita para o convênio " + rc.getRpcConvenio().getConNome() + " ainda possui " + quantidadeRestante + " exame" + (quantidadeRestante > 1 ? "s": "") + " em aberto."));
			}
			return pendencias;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return new ArrayList<Pendencia>();
		} 
	}

	public Class<?> getClassName() {
		return null;
	}
}