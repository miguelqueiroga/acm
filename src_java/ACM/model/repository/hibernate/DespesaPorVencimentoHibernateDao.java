package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.DespesaExtra;
import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.Vencimento;

@Repository(value="DespesaPorVencimentoRepository")
public class DespesaPorVencimentoHibernateDao extends HibernateDao {
	@Autowired
	public DespesaPorVencimentoHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_DespesaPorVencimento) {
		DespesaPorVencimento des = (DespesaPorVencimento) p_DespesaPorVencimento;
		if (des.getDpvId() != null)
			if (des.getDpvId() == 0)
				des.setDpvId(null);
		
		if(des.getDespesaExtraCollection() != null) {
			for(DespesaExtra de : des.getDespesaExtraCollection()){
				if (de.getDeId() != null)
					if (de.getDeId() == 0)
						de.setDeId(null);
			}
		}
		getHibernateTemplate().saveOrUpdate(des);
		return des;// ID POPULADA
	}
	
	public List<DespesaPorVencimento> getListPorVencimento(Vencimento r) {
		return getListPorVencimentoString(r.getVenNome());
	}

	@SuppressWarnings("unchecked")
	public List<DespesaPorVencimento> getListPorVencimentoString(String s) {
		List<DespesaPorVencimento> receitas = (List<DespesaPorVencimento>) getHibernateTemplate().find("from DespesaPorVencimento as r Where r.dpvVencimento.venNome = '" + s + "'");
		return receitas;
	}

	public Class<DespesaPorVencimento> getClassName() {
		return DespesaPorVencimento.class;
	}
}