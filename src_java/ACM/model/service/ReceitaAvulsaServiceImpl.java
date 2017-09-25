package ACM.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.ReceitaAvulsaHibernateDao;

@Service(value="receitaAvulsaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ReceitaAvulsaServiceImpl implements ReceitaAvulsaService {

	private ReceitaAvulsaHibernateDao ReceitaAvulsaHibernateDao;

	@Autowired
	public void setReceitaAvulsaRepository(ReceitaAvulsaHibernateDao ReceitaAvulsaHibernateDao) {
		this.ReceitaAvulsaHibernateDao = ReceitaAvulsaHibernateDao;
	}

	public ReceitaAvulsa save(ReceitaAvulsa receitaAvulsa) throws Exception {
		try {
			this.ReceitaAvulsaHibernateDao.save(receitaAvulsa);
			return receitaAvulsa;
		} catch (Exception e) {
			throw new Exception("Não foi possível salvar." +e.getCause());
		}
	}

	public void remove(ReceitaAvulsa receitaAvulsa) throws Exception {
		try {	
			this.ReceitaAvulsaHibernateDao.remove(receitaAvulsa);
		} catch (Exception e) {
			throw new Exception("Não foi possível excluir." +e.getMessage());
		}
	}

	public List<ReceitaAvulsa> getListPorVencimento(Vencimento r) throws Exception {
		try {
			return this.ReceitaAvulsaHibernateDao.getListPorVencimento(r);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

}