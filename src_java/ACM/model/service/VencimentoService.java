package ACM.model.service;

import java.util.List;

import ACM.model.entity.Vencimento;


public interface VencimentoService {
	
	void remove(Vencimento p_Usuario) throws Exception;
	Vencimento save(Vencimento p_Usuario) throws Exception;
	List<Vencimento> getList() throws Exception;
	Vencimento getVencimentoAtual() throws Exception;	
	
}