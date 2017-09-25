package ACM.model.service;

import java.util.List;

import ACM.model.entity.DepartamentoDepartamento;


public interface DepartamentoDepartamentoService {
	
	void remove(DepartamentoDepartamento p_Usuario) throws Exception;
	DepartamentoDepartamento save(DepartamentoDepartamento p_Usuario) throws Exception;
	List<DepartamentoDepartamento> getList() throws Exception;
	
}