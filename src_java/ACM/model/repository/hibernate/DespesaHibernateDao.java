package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Despesa;
import ACM.model.entity.Vencimento;

@Repository(value="DespesaRepository")
public class DespesaHibernateDao extends HibernateDao {
	@Autowired
	public DespesaHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Despesa) {
		Despesa des = (Despesa) p_Despesa;
		if (des.getDesId() != null)
			if (des.getDesId() == 0)
				des.setDesId(null);
		
		getHibernateTemplate().saveOrUpdate(des);
		return des;// ID POPULADA
	}
	
	public List<Despesa> getListPorVencimento(Vencimento r) {
		return getListPorVencimentoString(r.getVenNome());
	}

	@SuppressWarnings("unchecked")
	public List<Despesa> getListPorVencimentoString(String s) {
		List<Despesa> receitas = (List<Despesa>) getHibernateTemplate().find("from Despesa as r Where r.desVencimento.venNome = '" + s + "'");
		return receitas;
	}

	public Class<Despesa> getClassName() {
		return Despesa.class;
	}
}