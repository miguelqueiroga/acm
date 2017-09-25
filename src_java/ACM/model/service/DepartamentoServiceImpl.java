package ACM.model.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Departamento;
import ACM.model.entity.Socio;
import ACM.model.repository.hibernate.DepartamentoHibernateDao;

@Service(value="departamentoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DepartamentoServiceImpl implements DepartamentoService {

	private DepartamentoHibernateDao DepartamentoHibernateDao;

	@Autowired
	public void setDepartamentoRepository(DepartamentoHibernateDao DepartamentoHibernateDao) {
		this.DepartamentoHibernateDao = DepartamentoHibernateDao;
	}

	public Departamento save(Departamento p_Usuario) throws Exception {
		try {
			this.DepartamentoHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outro Departamento com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outro Departamento com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Departamento p_Usuario) throws Exception {
		try {	
			this.DepartamentoHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<Departamento> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			List<Departamento> listaTudo = this.DepartamentoHibernateDao.listaTudo();
			Collections.sort (listaTudo, new Comparator<Departamento>() {  
	            public int compare(Departamento o1, Departamento o2) {  
	                return o1.getDepNome().compareTo(o2.getDepNome());  
	            }  
	        });				
			return listaTudo;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
	
	public List<Departamento> getListPorSocio(Socio s) throws Exception {
		try {
			List<Departamento> departamentos = this.DepartamentoHibernateDao.listaTudo();
			List<Departamento> departamentosSocio = new ArrayList<Departamento>();
			for(Departamento dep : departamentos) {
				if(s.isDono(dep))
					departamentosSocio.add(dep);
			}
			Collections.sort (departamentosSocio, new Comparator<Departamento>() {  
	            public int compare(Departamento o1, Departamento o2) {  
	                return o1.getDepNome().compareTo(o2.getDepNome());  
	            }  
	        });				
			
			return departamentosSocio;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}