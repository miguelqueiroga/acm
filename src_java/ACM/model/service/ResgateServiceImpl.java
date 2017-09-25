package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Resgate;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.ResgateHibernateDao;

@Service(value = "resgateService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class ResgateServiceImpl implements ResgateService {

	private ResgateHibernateDao ResgateHibernateDao;

	@Autowired
	public void setUsuarioRepository(ResgateHibernateDao ResgateHibernateDao) {
		this.ResgateHibernateDao = ResgateHibernateDao;
	}

	public Resgate save(Resgate p_Resgate) throws Exception {
		try {
			this.ResgateHibernateDao.save(p_Resgate);
			return p_Resgate;
		} catch (DataIntegrityViolationException e) {
			throw new Exception(
					"Não foi possível salvar. \nExiste outro Resgate com o mesmo nome!");
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException)
				throw new Exception(
						"Não foi possível salvar. \nExiste outro Resgate com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Resgate p_Resgate) throws Exception {
		try {
			this.ResgateHibernateDao.remove(p_Resgate);
		} catch (DataIntegrityViolationException e) {
			throw new Exception(
					"Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException)
				throw new Exception(
						"Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<Resgate> getList() throws Exception {
		try {
			return this.ResgateHibernateDao.listaTudo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}

	public List<Resgate> getListPorVencimento(Vencimento v)
			throws Exception {
		try {
			// System.out.println(this.UsuarioRepository.getList().size());
			List<Resgate> resultado = this.ResgateHibernateDao
					.getListPorVencimento(v);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}

	public List<Resgate> getListPorVencimentoString(String s)
			throws Exception {
		try {
			return this.ResgateHibernateDao
					.getListPorVencimentoString(s);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}
}