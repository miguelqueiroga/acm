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
			throw new Exception("Não foi possível salvar. \nEste Departamento já foi relacionado como dono do Departamento atual!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nEste Departamento já foi relacionado como dono do Departamento atual!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(DepartamentoDepartamento p_Usuario) throws Exception {
		try {	
			this.DepartamentoDepartamentoHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<DepartamentoDepartamento> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.DepartamentoDepartamentoHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}