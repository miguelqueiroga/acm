package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

@Repository(value="ConsultaRepository")
public class ConsultaHibernateDao extends HibernateDao {
	
	@Autowired
	public ConsultaHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}
	
	public Class<Object> getClassName() {
		return Object.class;
	}
}