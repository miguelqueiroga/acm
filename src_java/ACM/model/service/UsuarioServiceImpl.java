package ACM.model.service;

import java.util.List;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import ACM.model.entity.Usuario;
import ACM.model.repository.hibernate.UsuarioHibernateDao;

@Service(value="usuarioService")
@Transactional(propagation=Propagation.REQUIRED, rollbackFor=Exception.class)
public class UsuarioServiceImpl implements UsuarioService {

	private UsuarioHibernateDao UsuarioHibernateDao;

	@Autowired
	public void setUsuarioHibernateDao(UsuarioHibernateDao UsuarioHibernateDao) {
		this.UsuarioHibernateDao = UsuarioHibernateDao;
	}

	public Usuario save(Usuario p_Usuario) throws Exception {
		try {
			this.UsuarioHibernateDao.save(p_Usuario);
			return p_Usuario;
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível salvar. \nExiste outro Usuário com o mesmo nome!");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException )
				throw new Exception("Não foi possível salvar. \nExiste outro Usuário com o mesmo nome!");
			throw new Exception("Não foi possível salvar. " + e.getMessage());
		}
	}

	public void remove(Usuario p_Usuario) throws Exception {
		try {	
			this.UsuarioHibernateDao.remove(p_Usuario);
		} catch (DataIntegrityViolationException e) {
			throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
		} catch (Exception e) {
			if ( e.getCause() instanceof ConstraintViolationException ) 
				throw new Exception("Não foi possível excluir. \nOutro cadastro faz referência ao que está tentando ser excluído");
			throw new Exception("Não foi possível excluir. " + e.getMessage());
		}
	}

	public List<Usuario> getList() throws Exception {
		try {
			//System.out.println(this.UsuarioHibernateDao.getList().size());
			return this.UsuarioHibernateDao.listaTudo();
		} catch (Exception e) {
			throw new Exception("Não foi possível listar."+e.getMessage());
		}
	}

	public Usuario login(String login, String senha) throws Exception {
		try {
			Usuario res = this.UsuarioHibernateDao.login(login, senha);
			//System.out.println(res);
			return res;
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception("Não foi possível entrar no sistema."+e.getMessage());
		}
	}
}