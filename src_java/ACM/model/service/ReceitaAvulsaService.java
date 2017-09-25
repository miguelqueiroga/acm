package ACM.model.service;

import java.util.List;

import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.Vencimento;


public interface ReceitaAvulsaService {
	
	void remove(ReceitaAvulsa p_Receita) throws Exception;
	ReceitaAvulsa save(ReceitaAvulsa p_Receita) throws Exception;
	List<ReceitaAvulsa> getListPorVencimento(Vencimento r)throws Exception;
	
}