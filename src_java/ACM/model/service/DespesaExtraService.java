package ACM.model.service;

import java.util.Collection;
import java.util.List;

import ACM.model.entity.DespesaExtra;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;


public interface DespesaExtraService {
	
	void remove(DespesaExtra p_Usuario) throws Exception;
	DespesaExtra save(DespesaExtra p_Usuario) throws Exception;
	List<DespesaExtra> getList() throws Exception;
	List<DespesaExtra> getListPorVencimento(Vencimento v) throws Exception;
	List<DespesaExtra> getListPorVencimentoString(String s) throws Exception;
	List<DespesaExtra> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception;	
	
}