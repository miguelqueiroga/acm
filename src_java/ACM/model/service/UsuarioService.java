package ACM.model.service;

import java.util.List;

import ACM.model.entity.Usuario;


public interface UsuarioService {
	
	Usuario login(String login, String senha) throws Exception;
	void remove(Usuario p_Usuario) throws Exception;
	Usuario save(Usuario p_Usuario) throws Exception;
	List<Usuario> getList() throws Exception;
	
}