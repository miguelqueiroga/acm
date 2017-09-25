package ACM.model.service;

import java.util.List;


public interface ConsultaService {
	
	<T> List<T> getListPorConsulta(String consulta) throws Exception;
	
}