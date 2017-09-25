package ACM.model.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.DepartamentoSocio;
import ACM.model.entity.Socio;
import ACM.model.repository.hibernate.DepartamentoSocioHibernateDao;

@Service(value="departamentoSocioService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DepartamentoSocioServiceImpl implements DepartamentoSocioService {

	private DepartamentoSocioHibernateDao DepartamentoSocioHibernateDao;

	@Autowired
	public void setUsuarioRepository(DepartamentoSocioHibernateDao DepartamentoSocioHibernateDao) {
		this.DepartamentoSocioHibernateDao = DepartamentoSocioHibernateDao;
	}

	public DepartamentoSocio save(DepartamentoSocio p_Usuario) throws Exception {
		try {
			this.DepartamentoSocioHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nEste Departamento já foi relacionado ao Sócio selecionado!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nEste Departamento já foi relacionado ao Sócio selecionado!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(DepartamentoSocio p_Usuario) throws Exception {
		try {	
			this.DepartamentoSocioHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<DepartamentoSocio> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.DepartamentoSocioHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<DepartamentoSocio> getListPorSocio(Socio s) throws Exception {
		try {
			Collection<DepartamentoSocio> departamentos = this.DepartamentoSocioHibernateDao.getListPorSocio(s); 
			return new ArrayList<DepartamentoSocio>(departamentos);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}