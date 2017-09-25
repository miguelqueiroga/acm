package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.Vencimento;

@Repository(value="ReceitaAvulsaRepository")
public class ReceitaAvulsaHibernateDao extends HibernateDao {
	@Autowired
	public ReceitaAvulsaHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_ReceitaAvulsa) {
		ReceitaAvulsa ra = (ReceitaAvulsa) p_ReceitaAvulsa;
		if (ra.getRaId() != null)
			if (ra.getRaId() == 0)
				ra.setRaId(null);
		
		getHibernateTemplate().saveOrUpdate(ra);
		return ra;// ID POPULADA
	}

	@SuppressWarnings("unchecked")
	public List<ReceitaAvulsa> getListPorVencimento(Vencimento r) {
		long idVencimento = r.getVenId();
		List<ReceitaAvulsa> receitas = (List<ReceitaAvulsa>) getHibernateTemplate().find("from ReceitaAvulsa as r Where r.raVencimento.venId = " + idVencimento);
		return receitas;
	}

	public Class<ReceitaAvulsa> getClassName() {
		return ReceitaAvulsa.class;
	}
}