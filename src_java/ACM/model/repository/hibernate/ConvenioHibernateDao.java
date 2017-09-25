package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Convenio;

@Repository(value="ConvenioRepository")
public class ConvenioHibernateDao extends HibernateDao {
	@Autowired
	public ConvenioHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Convenio) {
		Convenio c = (Convenio) p_Convenio;
		if (c.getConId() != null)
			if (c.getConId() == 0)
				c.setConId(null);
		
		getHibernateTemplate().saveOrUpdate(c);
		return c;// ID POPULADA
	}

	public Class<Convenio> getClassName() {
		return Convenio.class;
	}	
}