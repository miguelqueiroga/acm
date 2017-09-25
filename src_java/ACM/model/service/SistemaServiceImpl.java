package ACM.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.repository.hibernate.SistemaHibernateDao;
import ACM.util.Pendencia;

@Service(value="sistemaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class SistemaServiceImpl implements SistemaService {

	private SistemaHibernateDao SistemaHibernateDao;

	@Autowired
	public void setUsuarioRepository(SistemaHibernateDao SistemaHibernateDao) {
		this.SistemaHibernateDao = SistemaHibernateDao;
	}

	public boolean finalizaMes(Boolean removeZeradas) {
		return this.SistemaHibernateDao.finalizaMes(removeZeradas);		
	}

	public List<Pendencia> getPendenciasFechamento() {
		return this.SistemaHibernateDao.getPendenciasFechamento();
	}
}