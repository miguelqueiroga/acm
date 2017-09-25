package ACM.model.service;

import java.util.Collection;
import java.util.List;

import ACM.model.entity.DespesaPorVencimento;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;


public interface DespesaPorVencimentoService {
	
	void remove(DespesaPorVencimento p_Usuario) throws Exception;
	DespesaPorVencimento save(DespesaPorVencimento p_Usuario) throws Exception;
	List<DespesaPorVencimento> getList() throws Exception;
	List<DespesaPorVencimento> getListPorVencimento(Vencimento v) throws Exception;
	List<DespesaPorVencimento> getListPorVencimentoString(String s) throws Exception;
	List<DespesaPorVencimento> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception;
	String getRelatorioGeralDespesas(List<DespesaPorVencimento> despesas, String url) throws Exception;
	
}