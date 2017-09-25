package ACM.model.service;

import java.util.Collection;
import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.DespesaExtra;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;
import ACM.model.repository.hibernate.DespesaExtraHibernateDao;

@Service(value="despesaExtraService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class DespesaExtraServiceImpl implements DespesaExtraService {

	private DespesaExtraHibernateDao DespesaExtraHibernateDao;

	@Autowired
	public void setUsuarioRepository(DespesaExtraHibernateDao DespesaExtraHibernateDao) {
		this.DespesaExtraHibernateDao = DespesaExtraHibernateDao;
	}

	public DespesaExtra save(DespesaExtra p_Usuario) throws Exception {
		try {
			this.DespesaExtraHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outra DespesaExtra com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outra DespesaExtra com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(DespesaExtra p_Usuario) throws Exception {
		try {	
			this.DespesaExtraHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<DespesaExtra> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.DespesaExtraHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<DespesaExtra> getListPorVencimento(Vencimento v)
			throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			List<DespesaExtra> resultado = this.DespesaExtraHibernateDao.getListPorVencimento(v);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<DespesaExtra> getListPorVencimentoString(String s) throws Exception {
		try {
			return this.DespesaExtraHibernateDao.getListPorVencimentoString(s);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<DespesaExtra> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception {
		try {
			List<DespesaExtra> resultado = this.DespesaExtraHibernateDao.listaPorCriterios(criterios); 
			System.out.println(resultado);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}