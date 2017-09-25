package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Convenio;
import ACM.model.entity.ReceitaPorConvenio;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.ReceitaPorConvenioHibernateDao;

@Service(value="receitaPorConvenioService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ReceitaPorConvenioServiceImpl implements ReceitaPorConvenioService {

	private ReceitaPorConvenioHibernateDao ReceitaPorConvenioHibernateDao;

	@Autowired
	public void setUsuarioRepository(ReceitaPorConvenioHibernateDao ReceitaPorConvenioHibernateDao) {
		this.ReceitaPorConvenioHibernateDao = ReceitaPorConvenioHibernateDao;
	}

	public ReceitaPorConvenio save(ReceitaPorConvenio rpc) throws Exception {
		try {
			System.out.println(rpc.toString());
			this.ReceitaPorConvenioHibernateDao.save(rpc);
			return rpc;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nEste Convênio já foi cadastrado para o Vencimento atual!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nEste Convênio já foi cadastrado para o Vencimento atual!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(ReceitaPorConvenio p_Usuario) throws Exception {
		try {	
			this.ReceitaPorConvenioHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<ReceitaPorConvenio> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ReceitaPorConvenioHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<ReceitaPorConvenio> getListPorVencimento(Vencimento v)
			throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			List<ReceitaPorConvenio> result = this.ReceitaPorConvenioHibernateDao.getListPorVencimento(v);
			return result;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<ReceitaPorConvenio> getListPorVencimentoString(String v)
			throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ReceitaPorConvenioHibernateDao.getListPorVencimentoString(v);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public ReceitaPorConvenio getConvenioPorVencimento(Convenio c, Vencimento v)
			throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ReceitaPorConvenioHibernateDao.getConvenioPorVencimento(c, v);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}
}