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

import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.VencimentoHibernateDao;

@Service(value="vencimentoService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class VencimentoServiceImpl implements VencimentoService {

	private VencimentoHibernateDao VencimentoHibernateDao;

	@Autowired
	public void setUsuarioRepository(VencimentoHibernateDao VencimentoHibernateDao) {
		this.VencimentoHibernateDao = VencimentoHibernateDao;
	}

	public Vencimento save(Vencimento p_Usuario) throws Exception {
		try {
			this.VencimentoHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("N�o foi poss�vel salvar. \nExiste outro Vencimento para o m�s e ano selecionado!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("N�o foi poss�vel salvar. \nExiste outro Vencimento para o m�s e ano selecionado!");
			throw new Exception("N�o foi poss�vel salvar. " + e.getMessage());
		}
	}

	public void remove(Vencimento p_Usuario) throws Exception {
		try {	
			this.VencimentoHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("N�o foi poss�vel excluir. \nOutro cadastro faz refer�ncia ao que est� tentando ser exclu�do");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("N�o foi poss�vel excluir. \nOutro cadastro faz refer�ncia ao que est� tentando ser exclu�do");
			throw new Exception("N�o foi poss�vel excluir. " + e.getMessage());
		}
	}
	public List<Vencimento> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioRepository.getList().size());
			List<Vencimento> listaTudo = this.VencimentoHibernateDao.listaTudo();
			Collections.sort(listaTudo, new Comparator<Vencimento>(){
				public int compare(Vencimento ven1, Vencimento ven2) {
					return ven2.getVenNome().compareTo(ven1.getVenNome());
				}				
			});
			return listaTudo;
		} catch (Exception e) {
			throw new Exception("N�o foi poss�vel listar."+e.getMessage());
		}
	}

	public Vencimento getVencimentoAtual() throws Exception {
		try {	
			return this.VencimentoHibernateDao.getVencimentoAtual();
		} catch (Exception e) {
			throw new Exception("N�o foi poss�vel procurar vencimento atual."+e.getMessage());
		}
	}
}