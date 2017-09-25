package ACM.model.service;

import java.util.List;

import ACM.model.entity.ReceitaAvulsa;
import ACM.model.entity.ResumoAnual;


public interface RelatorioService {
	
	List<ResumoAnual> getListResumosAnuais(String socioId, String dataInicial, String dataFinal, List<ReceitaAvulsa> receitasAvulsas)throws Exception;
	
}