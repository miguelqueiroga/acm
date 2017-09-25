package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Convenio;
import ACM.model.entity.Receita;
import ACM.model.entity.ReceitaPorConvenio;
import ACM.model.entity.Vencimento;

@Repository(value="ReceitaPorConvenioRepository")
public class ReceitaPorConvenioHibernateDao extends HibernateDao {
	@Autowired
	public ReceitaPorConvenioHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}	

	public Object save(Object p_ReceitaPorConvenio) {
		ReceitaPorConvenio rpc = (ReceitaPorConvenio) p_ReceitaPorConvenio;
		if (rpc.getRpcId() != null)
			if (rpc.getRpcId() == 0)
				rpc.setRpcId(null);
		
		for(Receita rec : rpc.getReceitasCollection()){
			if (rec.getRecId() != null)
				if (rec.getRecId() == 0)
					rec.setRecId(null);
		}
		
		getHibernateTemplate().saveOrUpdate(rpc);
		return rpc;// ID POPULADA
	}
	
	public List<ReceitaPorConvenio> getListPorVencimento(Vencimento v) {
		return getListPorVencimentoString(v.getVenNome());
	}	

	@SuppressWarnings("unchecked")
	public List<ReceitaPorConvenio> getListPorVencimentoString(String v) {
		List<ReceitaPorConvenio> receitas = (List<ReceitaPorConvenio>) getHibernateTemplate().find("from ReceitaPorConvenio as r Where r.rpcVencimento.venNome LIKE '" + v + "'");
		/*for(ReceitaPorConvenio rc : receitas) {
			double aliquotaIR = Tools.stringToDouble(rc.getRpcAliquotaIR()) / 100;
			double deducaoIR = Tools.stringToDouble(rc.getRpcDeducaoIR());
			double exames = Tools.stringToDouble(rc.getRpcValorExames());
			double materiais = Tools.stringToDouble(rc.getRpcValorMateriais());
			double filmes = Tools.stringToDouble(rc.getRpcValorFilmes());
			double medicamentos = Tools.stringToDouble(rc.getRpcValorMedicamentos());
			double valorReceitas = exames + materiais + filmes + medicamentos;
			rc.setRpcValorBrutoTotal(Tools.doubleToString(valorReceitas));
			double inss = Tools.stringToDouble(rc.getRpcINSSTotal());
			double irTotal = (aliquotaIR * (valorReceitas - inss)) - deducaoIR;
			rc.setRpcIRTotal(Tools.doubleToString(irTotal));
			double pagPart = Tools.stringToDouble(rc.getRpcPagamentoParticular());
			double outras = Tools.stringToDouble(rc.getRpcOutrasDespesas());
			double totalDebitos = outras + pagPart + inss + irTotal;
			rc.setRpcValorDespesasTotal(Tools.doubleToString(totalDebitos));
			double valorCreditado = valorReceitas - totalDebitos;
			rc.setRpcValorCreditado(Tools.doubleToString(valorCreditado));
			this.save(rc);
		}		*/
		return receitas;
	}

	public Class<ReceitaPorConvenio> getClassName() {
		return ReceitaPorConvenio.class;
	}

	public ReceitaPorConvenio getConvenioPorVencimento(Convenio c, Vencimento v) {
		List<ReceitaPorConvenio> receitas = (List<ReceitaPorConvenio>) getHibernateTemplate().find("from ReceitaPorConvenio as r Where r.rpcVencimento.venNome LIKE '" + v.getVenNome() + "' AND r.rpcConvenio.conNome LIKE '" + c.getConNome() + "'");
		if(receitas.size() > 0)
			return receitas.get(0);
		else return null;
	}	
}