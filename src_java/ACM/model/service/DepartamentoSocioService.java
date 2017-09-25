package ACM.model.service;

import java.util.List;

import ACM.model.entity.DepartamentoSocio;
import ACM.model.entity.Socio;


public interface DepartamentoSocioService {
	
	void remove(DepartamentoSocio p_Usuario) throws Exception;
	DepartamentoSocio save(DepartamentoSocio p_Usuario) throws Exception;
	List<DepartamentoSocio> getList() throws Exception;
	List<DepartamentoSocio> getListPorSocio(Socio s) throws Exception;
	
}