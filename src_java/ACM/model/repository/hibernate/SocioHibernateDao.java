package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Socio;

@Repository(value="SocioRepository")
public class SocioHibernateDao extends HibernateDao {
	@Autowired
	public SocioHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}
	
	public Object save(Object p_Socio) {
		Socio soc = (Socio) p_Socio;
		if (soc.getSocId() != null)
			if (soc.getSocId() == 0)
				soc.setSocId(null);
		
		getHibernateTemplate().saveOrUpdate(soc);
		return soc;// ID POPULADA
	}

	public Class<Socio> getClassName() {
		return Socio.class;
	}	
}