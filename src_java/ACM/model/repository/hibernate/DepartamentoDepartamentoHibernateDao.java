package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.DepartamentoDepartamento;

@Repository(value="DepartamentoDepartamentoRepository")
public class DepartamentoDepartamentoHibernateDao extends HibernateDao {
	@Autowired
	public DepartamentoDepartamentoHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}
	
	public Class<DepartamentoDepartamento> getClassName() {
		return DepartamentoDepartamento.class;
	}
	
	public Object save(Object p_Usuario) {
		DepartamentoDepartamento dd = (DepartamentoDepartamento) p_Usuario;
		if(dd.getDdId() != null && dd.getDdId() == 0)
			dd.setDdId(null);
		return super.save(dd);// ID POPULADA
	}	
}