package ACM.model.service;

import java.util.List;

import ACM.model.entity.Convenio;
import ACM.model.entity.ReceitaPorConvenio;
import ACM.model.entity.Vencimento;


public interface ReceitaPorConvenioService {
	
	void remove(ReceitaPorConvenio p_Usuario) throws Exception;
	ReceitaPorConvenio save(ReceitaPorConvenio p_Usuario) throws Exception;
	List<ReceitaPorConvenio> getList() throws Exception;
	List<ReceitaPorConvenio> getListPorVencimento(Vencimento v) throws Exception;
	List<ReceitaPorConvenio> getListPorVencimentoString(String v) throws Exception;
	ReceitaPorConvenio getConvenioPorVencimento(Convenio c, Vencimento v) throws Exception;	
	
}