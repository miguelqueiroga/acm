package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.ReceitaAvulsaSocio;

@Repository(value="ReceitaAvulsaSocioRepository")
public class ReceitaAvulsaSocioHibernateDao extends HibernateDao {
	@Autowired
	public ReceitaAvulsaSocioHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_ReceitaAvulsa) {
		ReceitaAvulsaSocio ra = (ReceitaAvulsaSocio) p_ReceitaAvulsa;
		if (ra.getRasId() != null)
			if (ra.getRasId() == 0)
				ra.setRasId(null);
		
		getHibernateTemplate().saveOrUpdate(ra);
		return ra;// ID POPULADA
	}

	public Class<ReceitaAvulsaSocio> getClassName() {
		return ReceitaAvulsaSocio.class;
	}
}