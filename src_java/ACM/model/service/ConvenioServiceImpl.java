package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Convenio;
import ACM.model.repository.hibernate.ConvenioHibernateDao;

@Service(value="convenioService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ConvenioServiceImpl implements ConvenioService {

	private ConvenioHibernateDao ConvenioHibernateDao;

	@Autowired
	public void setUsuarioRepository(ConvenioHibernateDao ConvenioHibernateDao) {
		this.ConvenioHibernateDao = ConvenioHibernateDao;
	}

	public Convenio save(Convenio p_Usuario) throws Exception {
		try {
			this.ConvenioHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outro Convênio com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outro Convênio com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Convenio p_Usuario) throws Exception {
		try {	
			this.ConvenioHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<Convenio> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ConvenioHibernateDao.listaTudo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}