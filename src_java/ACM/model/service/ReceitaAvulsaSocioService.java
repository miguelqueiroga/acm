package ACM.model.service;

import ACM.model.entity.ReceitaAvulsaSocio;


public interface ReceitaAvulsaSocioService {
	
	void remove(ReceitaAvulsaSocio p_Receita) throws Exception;
	ReceitaAvulsaSocio save(ReceitaAvulsaSocio p_Receita) throws Exception;
	
}