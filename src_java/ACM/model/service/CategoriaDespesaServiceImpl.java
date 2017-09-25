package ACM.model.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.CategoriaDespesa;
import ACM.model.repository.hibernate.CategoriaDespesaHibernateDao;

@Service(value="categoriaDespesaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class CategoriaDespesaServiceImpl implements CategoriaDespesaService {

	private CategoriaDespesaHibernateDao CategoriaDespesaHibernateDao;

	@Autowired
	public void setUsuarioRepository(CategoriaDespesaHibernateDao CategoriaDespesaHibernateDao) {
		this.CategoriaDespesaHibernateDao = CategoriaDespesaHibernateDao;
	}

	public CategoriaDespesa save(CategoriaDespesa p_Usuario) throws Exception {
		try {
			this.CategoriaDespesaHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outra Categoria de Despesa com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outra Categoria de Despesa com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(CategoriaDespesa p_Usuario) throws Exception {
		try {	
			this.CategoriaDespesaHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<CategoriaDespesa> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			List<CategoriaDespesa> resultado = this.CategoriaDespesaHibernateDao.listaTudo();
			Collections.sort (resultado, new Comparator<CategoriaDespesa>() {  
	            public int compare(CategoriaDespesa o1, CategoriaDespesa o2) {  
	                return o1.getCdNome().compareTo(o2.getCdNome());  
	            }  
	        });				
			
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}