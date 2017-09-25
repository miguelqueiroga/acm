package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Retirada;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.RetiradaHibernateDao;

@Service(value="retiradaService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class RetiradaServiceImpl implements RetiradaService {

	private RetiradaHibernateDao RetiradaHibernateDao;

	@Autowired
	public void setUsuarioRepository(RetiradaHibernateDao RetiradaHibernateDao) {
		this.RetiradaHibernateDao = RetiradaHibernateDao;
	}

	public Retirada save(Retirada p_Retirada) throws Exception {
		try {
			this.RetiradaHibernateDao.save(p_Retirada);
			return p_Retirada;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outro Retirada com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outro Retirada com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Retirada p_Usuario) throws Exception {
		try {	
			this.RetiradaHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<Retirada> getList() throws Exception {
		try {
			return this.RetiradaHibernateDao.listaTudo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public List<Retirada> getListPorVencimento(Vencimento v)
			throws Exception {
		try {
			// System.out.println(this.UsuarioRepository.getList().size());
			List<Retirada> resultado = this.RetiradaHibernateDao
					.getListPorVencimento(v);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}

	public List<Retirada> getListPorVencimentoString(String s)
			throws Exception {
		try {
			return this.RetiradaHibernateDao
					.getListPorVencimentoString(s);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}
}