package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Socio;
import ACM.model.repository.hibernate.SocioHibernateDao;

@Service(value="socioService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class SocioServiceImpl implements SocioService {

	private SocioHibernateDao SocioHibernateDao;

	@Autowired
	public void setSocioRepository(SocioHibernateDao SocioHibernateDao) {
		this.SocioHibernateDao = SocioHibernateDao;
	}

	public Socio save(Socio p_Usuario) throws Exception {
		try {
			this.SocioHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outro Sócio com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outro Sócio com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Socio p_Usuario) throws Exception {
		try {	
			this.SocioHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public <T> List<T> getList() throws Exception {
		try {
			return this.SocioHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}