package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Resgate;
import ACM.model.entity.Vencimento;

@Repository(value="ResgateRepository")
public class ResgateHibernateDao extends HibernateDao {
	@Autowired
	public ResgateHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Resgate) {
		Resgate cd = (Resgate) p_Resgate;
		if (cd.getResId() != null)
			if (cd.getResId() == 0)
				cd.setResId(null);
		
		getHibernateTemplate().saveOrUpdate(cd);
		return cd;// ID POPULADA
	}

	public Resgate findById(Resgate socio) throws Exception {
		long id = socio.getResId();
		socio = (Resgate) getHibernateTemplate().get(Resgate.class, socio.getResId());
		
		if (socio == null)
			throw new Exception("A Resgate com a ID: "+id+" não foi encontrada.");
		return socio;
	}

	public Class<Resgate> getClassName() {
		return Resgate.class;
	}
	
	public List<Resgate> getListPorVencimento(Vencimento r) {
		return getListPorVencimentoString(r.getVenNome());
	}

	@SuppressWarnings("unchecked")
	public List<Resgate> getListPorVencimentoString(String s) {
		List<Resgate> receitas = (List<Resgate>) getHibernateTemplate().find("from Resgate as a Where a.vencimento.venNome = '" + s + "'");
		return receitas;
	}
}