package ACM.model.service;

import java.util.List;

import ACM.model.entity.Resgate;
import ACM.model.entity.Vencimento;


public interface ResgateService {
	
	void remove(Resgate p_Resgate) throws Exception;
	Resgate save(Resgate p_Resgate) throws Exception;
	List<Resgate> getList() throws Exception;
	List<Resgate> getListPorVencimento(Vencimento v) throws Exception;
	List<Resgate> getListPorVencimentoString(String s) throws Exception;
}