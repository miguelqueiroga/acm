package ACM.model.service;

import java.util.List;

import ACM.model.entity.Departamento;
import ACM.model.entity.Socio;


public interface DepartamentoService {
	
	void remove(Departamento p_Usuario) throws Exception;
	Departamento save(Departamento p_Usuario) throws Exception;
	List<Departamento> getList() throws Exception;
	public List<Departamento> getListPorSocio(Socio s) throws Exception;
	
}