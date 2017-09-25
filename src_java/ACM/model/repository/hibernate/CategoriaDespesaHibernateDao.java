package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.CategoriaDespesa;

@Repository(value="CategoriaDespesaRepository")
public class CategoriaDespesaHibernateDao extends HibernateDao {
	@Autowired
	public CategoriaDespesaHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_CategoriaDespesa) {
		CategoriaDespesa cd = (CategoriaDespesa) p_CategoriaDespesa;
		if (cd.getCdId() != null)
			if (cd.getCdId() == 0)
				cd.setCdId(null);
		
		getHibernateTemplate().saveOrUpdate(cd);
		return cd;// ID POPULADA
	}

	public CategoriaDespesa findById(CategoriaDespesa socio) throws Exception {
		long id = socio.getCdId();
		socio = (CategoriaDespesa) getHibernateTemplate().get(CategoriaDespesa.class, socio.getCdId());
		
		if (socio == null)
			throw new Exception("O CategoriaDespesa com a ID: "+id+" não foi encontrado.");
		return socio;
	}

	public Class<CategoriaDespesa> getClassName() {
		return CategoriaDespesa.class;
	}
}