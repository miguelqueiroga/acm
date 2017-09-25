package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Vencimento;

@Repository(value="VencimentoRepository")
public class VencimentoHibernateDao extends HibernateDao {
	@Autowired
	public VencimentoHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Vencimento) {
		Vencimento ven = (Vencimento) p_Vencimento;
		if (ven.getVenId() != null)
			if (ven.getVenId() == 0)
				ven.setVenId(null);
		
		getHibernateTemplate().saveOrUpdate(ven);
		return ven;// ID POPULADA
	}

	public Vencimento getVencimentoAtual() {
		Vencimento vencimento = null; 
		List<Vencimento> vencimentos = listaTudo();
		for(Vencimento venc : vencimentos){
			if(vencimento == null || (vencimento.getVenId() < venc.getVenId()))
				vencimento = venc;
		}
		return vencimento;
	}

	public Class<Vencimento> getClassName() {
		return Vencimento.class;
	}
}