package ACM.model.service;

import java.util.List;

import ACM.model.entity.Retirada;
import ACM.model.entity.Vencimento;


public interface RetiradaService {
	
	void remove(Retirada p_Retirada) throws Exception;
	Retirada save(Retirada p_Retirada) throws Exception;
	List<Retirada> getList() throws Exception;
	List<Retirada> getListPorVencimento(Vencimento v) throws Exception;
	List<Retirada> getListPorVencimentoString(String s) throws Exception;
	
	
}