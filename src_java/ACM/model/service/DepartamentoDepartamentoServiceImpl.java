package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.DepartamentoDepartamento;
import ACM.model.repository.hibernate.DepartamentoDepartamentoHibernateDao;

@Service(value="departamentoDepartamentoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DepartamentoDepartamentoServiceImpl implements DepartamentoDepartamentoService {

	private DepartamentoDepartamentoHibernateDao DepartamentoDepartamentoHibernateDao;

	@Autowired
	public void setUsuarioRepository(DepartamentoDepartamentoHibernateDao DepartamentoDepartamentoHibernateDao) {
		this.DepartamentoDepartamentoHibernateDao = DepartamentoDepartamentoHibernateDao;
	}

	public DepartamentoDepartamento save(DepartamentoDepartamento p_Usuario) throws Exception {
		try {
			System.out.println(p_Usuario.getDdDepartamentoDono().getDepNome());
			this.DepartamentoDepartamentoHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("N�o foi poss�vel salvar. \nEste Departamento j� foi relacionado como dono do Departamento atual!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("N�o foi poss�vel salvar. \nEste Departamento j� foi relacionado como dono do Departamento atual!");
			throw new Exception("N�o foi poss�vel salvar. " + e.getMessage());
		}
	}

	public void remove(DepartamentoDepartamento p_Usuario) throws Exception {
		try {	
			this.DepartamentoDepartamentoHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("N�o foi poss�vel excluir. \nOutro cadastro faz refer�ncia ao que est� tentando ser exclu�do");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("N�o foi poss�vel excluir. \nOutro cadastro faz refer�ncia ao que est� tentando ser exclu�do");
			throw new Exception("N�o foi poss�vel excluir. " + e.getMessage());
		}
	}

	public List<DepartamentoDepartamento> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.DepartamentoDepartamentoHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("N�o foi poss�vel listar."+e.getMessage());
		}
	}
}