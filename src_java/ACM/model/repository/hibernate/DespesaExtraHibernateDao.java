package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.DespesaExtra;
import ACM.model.entity.Vencimento;

@Repository(value="DespesaExtraRepository")
public class DespesaExtraHibernateDao extends HibernateDao {
	@Autowired
	public DespesaExtraHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_DespesaExtra) {
		DespesaExtra des = (DespesaExtra) p_DespesaExtra;
		if (des.getDeId() != null)
			if (des.getDeId() == 0)
				des.setDeId(null);
		
		getHibernateTemplate().saveOrUpdate(des);
		return des;// ID POPULADA
	}
	
	public List<DespesaExtra> getListPorVencimento(Vencimento r) {
		return getListPorVencimentoString(r.getVenNome());
	}

	@SuppressWarnings("unchecked")
	public List<DespesaExtra> getListPorVencimentoString(String s) {
		List<DespesaExtra> receitas = (List<DespesaExtra>) getHibernateTemplate().find("from DespesaExtra as r Where r.desVencimento.venNome = '" + s + "'");
		return receitas;
	}

	public Class<DespesaExtra> getClassName() {
		return DespesaExtra.class;
	}
}