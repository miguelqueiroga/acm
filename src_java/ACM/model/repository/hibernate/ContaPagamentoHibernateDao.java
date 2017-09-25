package ACM.model.repository.hibernate;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.ContaPagamento;

@Repository(value="ContaPagamentoRepository")
public class ContaPagamentoHibernateDao extends HibernateDao {
	@Autowired
	public ContaPagamentoHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_ContaPagamento) {
		ContaPagamento cp = (ContaPagamento) p_ContaPagamento;
		if (cp.getCpId() != null)
			if (cp.getCpId() == 0)
				cp.setCpId(null);
		
		getHibernateTemplate().saveOrUpdate(cp);
		return cp;// ID POPULADA
	}

	public Class<ContaPagamento> getClassName() {
		return ContaPagamento.class;
	}
}