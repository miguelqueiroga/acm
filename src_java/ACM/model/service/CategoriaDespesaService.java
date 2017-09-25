package ACM.model.service;

import java.util.List;

import ACM.model.entity.CategoriaDespesa;


public interface CategoriaDespesaService {
	
	void remove(CategoriaDespesa p_Usuario) throws Exception;
	CategoriaDespesa save(CategoriaDespesa p_Usuario) throws Exception;
	List<CategoriaDespesa> getList() throws Exception;
	
}