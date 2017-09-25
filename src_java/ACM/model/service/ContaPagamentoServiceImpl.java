package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.ContaPagamento;
import ACM.model.repository.hibernate.ContaPagamentoHibernateDao;

@Service(value="contaPagamentoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class ContaPagamentoServiceImpl implements ContaPagamentoService {

	private ContaPagamentoHibernateDao ContaPagamentoHibernateDao;

	@Autowired
	public void setUsuarioRepository(ContaPagamentoHibernateDao ContaPagamentoHibernateDao) {
		this.ContaPagamentoHibernateDao = ContaPagamentoHibernateDao;
	}

	public ContaPagamento save(ContaPagamento p_Usuario) throws Exception {
		try {
			this.ContaPagamentoHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("N�o foi poss�vel salvar. \nExiste outra Conta para Pagamento com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("N�o foi poss�vel salvar. \nExiste outra Conta para Pagamento com o mesmo nome!");
			throw new Exception("N�o foi poss�vel salvar. " + e.getMessage());
		}
	}

	public void remove(ContaPagamento p_Usuario) throws Exception {
		try {	
			this.ContaPagamentoHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("N�o foi poss�vel excluir. \nOutro cadastro faz refer�ncia ao que est� tentando ser exclu�do");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("N�o foi poss�vel excluir. \nOutro cadastro faz refer�ncia ao que est� tentando ser exclu�do");
			throw new Exception("N�o foi poss�vel excluir. " + e.getMessage());
		}
	}

	public List<ContaPagamento> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			return this.ContaPagamentoHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("N�o foi poss�vel listar."+e.getMessage());
		}
	}
}