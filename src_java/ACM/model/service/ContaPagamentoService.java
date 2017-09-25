package ACM.model.service;

import java.util.List;

import ACM.model.entity.ContaPagamento;


public interface ContaPagamentoService {
	
	void remove(ContaPagamento p_Usuario) throws Exception;
	ContaPagamento save(ContaPagamento p_Usuario) throws Exception;
	List<ContaPagamento> getList() throws Exception;
	
}