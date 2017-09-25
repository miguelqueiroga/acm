package ACM.model.service;

import java.util.Collection;
import java.util.List;

import ACM.model.entity.Receita;
import ACM.model.entity.Vencimento;
import ACM.model.repository.hibernate.CriterioDePesquisa;


public interface ReceitaService {
	
	void remove(Receita p_Usuario) throws Exception;
	Receita save(Receita p_Usuario) throws Exception;
	List<Receita> getList() throws Exception;
	List<Receita> getListPorVencimento(Vencimento r)throws Exception;
	List<Receita> buscaPorCriterios(Collection<CriterioDePesquisa> criterios) throws Exception;		
	
}