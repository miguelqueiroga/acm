package ACM.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.ReceitaAvulsaSocio;
import ACM.model.repository.hibernate.ReceitaAvulsaSocioHibernateDao;

@Service(value="receitaAvulsaSocioService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ReceitaAvulsaSocioServiceImpl implements ReceitaAvulsaSocioService {

	private ReceitaAvulsaSocioHibernateDao ReceitaAvulsaSocioHibernateDao;

	@Autowired
	public void setReceitaAvulsaSocioRepository(ReceitaAvulsaSocioHibernateDao ReceitaAvulsaSocioHibernateDao) {
		this.ReceitaAvulsaSocioHibernateDao = ReceitaAvulsaSocioHibernateDao;
	}

	public ReceitaAvulsaSocio save(ReceitaAvulsaSocio receitaAvulsaSocio) throws Exception {
		try {
			this.ReceitaAvulsaSocioHibernateDao.save(receitaAvulsaSocio);
			return receitaAvulsaSocio;
		} catch (Exception e) {
			throw new Exception("Não foi possível salvar." +e.getCause());
		}
	}

	public void remove(ReceitaAvulsaSocio receitaAvulsaSocio) throws Exception {
		try {	
			this.ReceitaAvulsaSocioHibernateDao.remove(receitaAvulsaSocio);
		} catch (Exception e) {
			throw new Exception("Não foi possível excluir." +e.getMessage());
		}
	}
}