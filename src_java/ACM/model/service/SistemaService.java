package ACM.model.service;

import java.util.List;

import ACM.util.Pendencia;


public interface SistemaService {
	
	List<Pendencia> getPendenciasFechamento();
	boolean finalizaMes(Boolean removeZeradas);
	
}