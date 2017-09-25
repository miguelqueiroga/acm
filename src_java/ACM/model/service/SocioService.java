package ACM.model.service;

import java.util.List;

import ACM.model.entity.Socio;


public interface SocioService {
	
	void remove(Socio p_Usuario) throws Exception;
	Socio save(Socio p_Usuario) throws Exception;
	<T> List<T> getList() throws Exception;
	
}