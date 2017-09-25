package ACM.model.service;

import java.util.Collection;
import java.util.List;

import ACM.model.entity.Despesa;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;


public interface DespesaService {
	
	void remove(Despesa p_Usuario) throws Exception;
	Despesa save(Despesa p_Usuario) throws Exception;
	List<Despesa> getList() throws Exception;
	List<Despesa> getListPorVencimento(Vencimento v) throws Exception;
	List<Despesa> getListPorVencimentoString(String s) throws Exception;
	List<Despesa> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception;	
	
}