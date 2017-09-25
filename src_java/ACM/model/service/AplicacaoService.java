package ACM.model.service;

import java.util.List;

import ACM.model.entity.Aplicacao;
import ACM.model.entity.Vencimento;


public interface AplicacaoService {
	
	void remove(Aplicacao p_Aplicacao) throws Exception;
	Aplicacao save(Aplicacao p_Aplicacao) throws Exception;
	List<Aplicacao> getList() throws Exception;
	List<Aplicacao> getListPorVencimento(Vencimento v) throws Exception;
	List<Aplicacao> getListPorVencimentoString(String s) throws Exception;
}