package ACM.model.repository.hibernate;

public class CriterioDePesquisaComparacaoNumero implements CriterioDePesquisa {
	
	private String operador = " = ";
	
	private String criterio;
	private Object valor;
	
	public CriterioDePesquisaComparacaoNumero(){
		
	}
	
	public CriterioDePesquisaComparacaoNumero(String criterio, Object valor){
		setCriterio(criterio);
		setValor(valor);
	}

	public String getCriterio() {
		return "o." + criterio;
	}

	public void setCriterio(String criterio) {
		this.criterio = criterio;
	}

	public Object getValor() {
		return valor;
	}

	public void setValor(Object valor) {
		this.valor = "" + valor + "";
	}

    public String getOperador() {
        return operador;
    }
    
    /**
	 * @param operador the operador to set
	 */
	public void setOperador(String operador) {
		this.operador = " " + operador + " ";
	}

}
