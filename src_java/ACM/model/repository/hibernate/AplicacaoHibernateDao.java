package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Aplicacao;
import ACM.model.entity.Vencimento;

@Repository(value="AplicacaoRepository")
public class AplicacaoHibernateDao extends HibernateDao {
	@Autowired
	public AplicacaoHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Aplicacao) {
		Aplicacao cd = (Aplicacao) p_Aplicacao;
		if (cd.getAplId() != null)
			if (cd.getAplId() == 0)
				cd.setAplId(null);
		
		getHibernateTemplate().saveOrUpdate(cd);
		return cd;// ID POPULADA
	}

	public Aplicacao findById(Aplicacao socio) throws Exception {
		long id = socio.getAplId();
		socio = (Aplicacao) getHibernateTemplate().get(Aplicacao.class, socio.getAplId());
		
		if (socio == null)
			throw new Exception("A Aplicacao com a ID: "+id+" não foi encontrada.");
		return socio;
	}

	public Class<Aplicacao> getClassName() {
		return Aplicacao.class;
	}
	
	public List<Aplicacao> getListPorVencimento(Vencimento r) {
		return getListPorVencimentoString(r.getVenNome());
	}

	@SuppressWarnings("unchecked")
	public List<Aplicacao> getListPorVencimentoString(String s) {
		List<Aplicacao> receitas = (List<Aplicacao>) getHibernateTemplate().find("from Aplicacao as a Where a.vencimento.venNome = '" + s + "'");
		return receitas;
	}
}