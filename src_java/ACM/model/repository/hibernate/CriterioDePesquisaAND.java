package ACM.model.repository.hibernate;

import java.util.Collection;
import java.util.Iterator;


public class CriterioDePesquisaAND implements CriterioDePesquisa{
	
	private String criterio = "(";
	private Collection<CriterioDePesquisa> criterios;
    private Object valor = ")";        
	
    public CriterioDePesquisaAND() {
		
	}
    
    public CriterioDePesquisaAND(Collection<CriterioDePesquisa> criterio){
		setCriterios(criterio);                
	}

	public String getCriterio() {
		return this.criterio;
	}

	public Collection<CriterioDePesquisa> getCriterios() {
		return this.criterios;
	}

	public void setCriterios(Collection<CriterioDePesquisa> criterios) {
		this.criterios = criterios;
	}
        
    public Object getValor() {
        return this.valor;
	}

    public String getOperador() {
    	String saida = "";
    	Iterator<CriterioDePesquisa> it = criterios.iterator();
    	while(it.hasNext()){
    		CriterioDePesquisa cp = it.next();
    		saida += cp.getCriterio() + cp.getOperador() + cp.getValor() + (it.hasNext() ? " AND ": "");    		
    	}
        return saida;
    }

}
