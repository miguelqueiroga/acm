package ACM.model.repository.hibernate;

import java.util.Collection;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.DepartamentoSocio;
import ACM.model.entity.Socio;

@Repository(value="DepartamentoSocioRepository")
public class DepartamentoSocioHibernateDao extends HibernateDao {
	@Autowired
	public DepartamentoSocioHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public <T> Collection<T> getListPorSocio(Socio s) {
		CriterioDePesquisaComparacaoNumero criterio = new CriterioDePesquisaComparacaoNumero();
		criterio.setCriterio("dsSocio.socId");
		criterio.setValor(s.getSocId());
		return super.listaPorCriterio(criterio);
	}
	
	public Class<DepartamentoSocio> getClassName() {
		return DepartamentoSocio.class;
	}
	
	public Object save(Object p_Usuario) {
		DepartamentoSocio ds = (DepartamentoSocio) p_Usuario;
		if(ds.getDsId() != null && ds.getDsId() == 0)
			ds.setDsId(null);
		return super.save(ds);// ID POPULADA
	}	
	
}