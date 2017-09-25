package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Aplicacao;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.AplicacaoHibernateDao;

@Service(value = "aplicacaoService")
@Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
public class AplicacaoServiceImpl implements AplicacaoService {

	private AplicacaoHibernateDao AplicacaoHibernateDao;

	@Autowired
	public void setUsuarioRepository(AplicacaoHibernateDao AplicacaoHibernateDao) {
		this.AplicacaoHibernateDao = AplicacaoHibernateDao;
	}

	public Aplicacao save(Aplicacao p_Aplicacao) throws Exception {
		try {
			this.AplicacaoHibernateDao.save(p_Aplicacao);
			return p_Aplicacao;
		} catch (DataIntegrityViolationException e) {
			throw new Exception(
					"Não foi possível salvar. \nExiste outro Aplicacao com o mesmo nome!");
		} catch (Exception e) {
			if (e.getCause() instanceof ConstraintViolationException)
				throw new Exception(
						"Não foi possível salvar. \nExiste outro Aplicacao com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Aplicacao p_Aplicacao) throws Exception {
		try {
			this.AplicacaoHibernateDao.remove(p_Aplicacao);
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

	public List<Aplicacao> getList() throws Exception {
		try {
			return this.AplicacaoHibernateDao.listaTudo();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}

	public List<Aplicacao> getListPorVencimento(Vencimento v)
			throws Exception {
		try {
			// System.out.println(this.UsuarioRepository.getList().size());
			List<Aplicacao> resultado = this.AplicacaoHibernateDao
					.getListPorVencimento(v);
			return resultado;
		} catch (Exception e) {
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}

	public List<Aplicacao> getListPorVencimentoString(String s)
			throws Exception {
		try {
			return this.AplicacaoHibernateDao
					.getListPorVencimentoString(s);
		} catch (Exception e) {
			throw new Exception("Não foi possível listar." + e.getMessage());
		}
	}
}