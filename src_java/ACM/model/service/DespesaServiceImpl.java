package ACM.model.service;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Despesa;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;
import ACM.model.repository.hibernate.DespesaHibernateDao;

@Service(value="despesaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DespesaServiceImpl implements DespesaService {

	private DespesaHibernateDao DespesaHibernateDao;

	@Autowired
	public void setUsuarioRepository(DespesaHibernateDao DespesaHibernateDao) {
		this.DespesaHibernateDao = DespesaHibernateDao;
	}

	public Despesa save(Despesa p_Usuario) throws Exception {
		try {
			this.DespesaHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outra Despesa com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outra Despesa com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Despesa p_Usuario) throws Exception {
		try {	
			this.DespesaHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<Despesa> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.DespesaHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<Despesa> getListPorVencimento(Vencimento v)
			throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			List<Despesa> resultado = this.DespesaHibernateDao.getListPorVencimento(v);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<Despesa> getListPorVencimentoString(String s) throws Exception {
		try {
			return this.DespesaHibernateDao.getListPorVencimentoString(s);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<Despesa> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception {
		try {
			List<Despesa> resultado = this.DespesaHibernateDao.listaPorCriterios(criterios); 
			Collections.sort (resultado, new Comparator<Despesa>() {  
	            public int compare(Despesa o1, Despesa o2) {  
	            	return o1.getDesNome().compareTo(o2.getDesNome());  
	            }  
	        });	
			System.out.println(resultado);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}