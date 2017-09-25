package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Receita;
import ACM.model.entity.Vencimento;

@Repository(value="ReceitaRepository")
public class ReceitaHibernateDao extends HibernateDao {
	@Autowired
	public ReceitaHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Receita) {
		Receita rec = (Receita) p_Receita;
		if (rec.getRecId() != null)
			if (rec.getRecId() == 0)
				rec.setRecId(null);
		
		getHibernateTemplate().saveOrUpdate(rec);
		return rec;// ID POPULADA
	}

	@SuppressWarnings("unchecked")
	public List<Receita> getListPorVencimento(Vencimento r) {
		long idVencimento = r.getVenId();
		List<Receita> receitas = (List<Receita>) getHibernateTemplate().find("from Receita as r Where r.recReceitaPorConvenio.rpcVencimento.venId = " + idVencimento);
		return receitas;
	}

	public Class<Receita> getClassName() {
		return Receita.class;
	}
}