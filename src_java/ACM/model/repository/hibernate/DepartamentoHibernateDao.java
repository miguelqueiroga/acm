package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Departamento;

@Repository(value="DepartamentoRepository")
public class DepartamentoHibernateDao extends HibernateDao {
	@Autowired
	public DepartamentoHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}
	
	public Object save(Object p_Departamento) {
		Departamento dep = (Departamento) p_Departamento;
		if (dep.getDepId() != null)
			if (dep.getDepId() == 0)
				dep.setDepId(null);
		
		getHibernateTemplate().saveOrUpdate(dep);
		return dep;// ID POPULADA
	}

	public Class<Departamento> getClassName() {
		return Departamento.class;
	}	
}