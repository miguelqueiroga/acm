package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Retirada;
import ACM.model.entity.Vencimento;

@Repository(value="RetiradaRepository")
public class RetiradaHibernateDao extends HibernateDao {
	@Autowired
	public RetiradaHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Retirada) {
		Retirada cd = (Retirada) p_Retirada;
		if (cd.getRetId() != null)
			if (cd.getRetId() == 0)
				cd.setRetId(null);
		
		getHibernateTemplate().saveOrUpdate(cd);
		return cd;// ID POPULADA
	}

	public Retirada findById(Retirada socio) throws Exception {
		long id = socio.getRetId();
		socio = (Retirada) getHibernateTemplate().get(Retirada.class, socio.getRetId());
		
		if (socio == null)
			throw new Exception("A Retirada com a ID: "+id+" não foi encontrada.");
		return socio;
	}

	public Class<Retirada> getClassName() {
		return Retirada.class;
	}
	
	public List<Retirada> getListPorVencimento(Vencimento r) {
		return getListPorVencimentoString(r.getVenNome());
	}

	@SuppressWarnings("unchecked")
	public List<Retirada> getListPorVencimentoString(String s) {
		List<Retirada> receitas = (List<Retirada>) getHibernateTemplate().find("from Retirada as r Where r.vencimento.venNome LIKE '" + s + "'");
		return receitas;
	}
}