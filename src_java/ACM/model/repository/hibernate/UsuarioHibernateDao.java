package ACM.model.repository.hibernate;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import ACM.model.entity.Usuario;

@Repository(value="UsuarioRepository")
public class UsuarioHibernateDao extends HibernateDao {
	@Autowired
	public UsuarioHibernateDao(@Qualifier("sessionFactory")SessionFactory factory) {
		super(factory);
	}

	public Object save(Object p_Usuario) {
		Usuario usu = (Usuario) p_Usuario;
		if (usu.getUsuId() != null)
			if (usu.getUsuId() == 0)
				usu.setUsuId(null);
		
		getHibernateTemplate().saveOrUpdate(usu);
		return usu;// ID POPULADA
	}
	
	public Usuario login(String login, String senha) {
		List<Usuario> usuarios = listaTudo(); 
		for(Usuario usuario : usuarios){
			if(usuario.getUsuLogin().equals(login) && usuario.getUsuSenha().equals(senha))
				return usuario;
		}
		return null;
	}

	public Class<Usuario> getClassName() {
		return Usuario.class;
	}

	
}