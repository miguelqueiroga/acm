package ACM.model.service;

import java.util.List;

import ACM.model.entity.Convenio;


public interface ConvenioService {
	
	void remove(Convenio p_Usuario) throws Exception;
	Convenio save(Convenio p_Usuario) throws Exception;
	List<Convenio> getList() throws Exception;
	
}